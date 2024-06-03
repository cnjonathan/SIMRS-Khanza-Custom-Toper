/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class ApiCareStream {
    private Connection koneksi=koneksiDB.condb();
    private Connection koneksifuji;
    private Connection koneksibridgingradiologi;
    private PreparedStatement ps, ps_dokter_radiologi;
    private ResultSet rs, rs_dokter_radiologi;
    private String URL="",requestJson="",stringbalik="", nama_dokter_radiologi="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private JsonNode root;
    private JsonNode response;
    private ObjectMapper mapper = new ObjectMapper();
    
    public ApiCareStream(){
        super();
        try {
            URL = koneksiDB.URLCARESTREAM();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public void kirimRalan(String nopermintaan) {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        try {
             ps=koneksi.prepareStatement(
                     "select \n" +
                        "  permintaan_radiologi.noorder, \n" +
                        "  permintaan_radiologi.no_rawat, \n" +
                        "  reg_periksa.tgl_registrasi as tgreg, \n" +
                        "  reg_periksa.kd_pj, \n" +
                        "  reg_periksa.jam_reg as jam, \n" +
                        "  reg_periksa.kd_poli, \n" +
                        "  drj.nm_dokter as perujuk, \n" +
                        "  poliklinik.nm_poli, \n" +
                        "  reg_periksa.no_rkm_medis, \n" +
                        "  jns_perawatan_radiologi.bagian_rs, \n" +
                        "  jns_perawatan_radiologi.bhp, \n" +
                        "  jns_perawatan_radiologi.tarif_perujuk, \n" +
                        "  jns_perawatan_radiologi.tarif_tindakan_dokter, \n" +
                        "  jns_perawatan_radiologi.tarif_tindakan_petugas, \n" +
                        "  jns_perawatan_radiologi.kso, \n" +
                        "  jns_perawatan_radiologi.menejemen, \n" +
                        "  jns_perawatan_radiologi.total_byr, \n" +
                        "  pemeriksaan_ralan.berat, \n" +
                        "  pasien.nm_pasien, \n" +
                        "  permintaan_radiologi.tgl_permintaan, \n" +
                        "  if(\n" +
                        "    permintaan_radiologi.jam_permintaan = '00:00:00', \n" +
                        "    '', permintaan_radiologi.jam_permintaan\n" +
                        "  ) as jam_permintaan, \n" +
                        "  pasien.tgl_lahir, \n" +
                        "  pasien.jk, \n" +
                        "  pasien.alamat, \n" +
                        "  if(\n" +
                        "    permintaan_radiologi.tgl_sampel = '0000-00-00', \n" +
                        "    '', permintaan_radiologi.tgl_sampel\n" +
                        "  ) as tgl_sampel, \n" +
                        "  if(\n" +
                        "    permintaan_radiologi.jam_sampel = '00:00:00', \n" +
                        "    '', permintaan_radiologi.jam_sampel\n" +
                        "  ) as jam_sampel, \n" +
                        "  if(\n" +
                        "    permintaan_radiologi.tgl_hasil = '0000-00-00', \n" +
                        "    '', permintaan_radiologi.tgl_hasil\n" +
                        "  ) as tgl_hasil, \n" +
                        "  if(\n" +
                        "    permintaan_radiologi.jam_hasil = '00:00:00', \n" +
                        "    '', permintaan_radiologi.jam_hasil\n" +
                        "  ) as jam_hasil, \n" +
                        "  permintaan_radiologi.dokter_perujuk, \n" +
                        "  poliklinik.nm_poli, \n" +
                        "  pasien.no_tlp, \n" +
                        "  penjab.png_jawab, \n" +
                        "  permintaan_pemeriksaan_radiologi.kd_jenis_prw, \n" +
                        "  jns_perawatan_radiologi.nm_perawatan, \n" +
                        "  permintaan_radiologi.diagnosa_klinis, \n" +
                        "  permintaan_radiologi.informasi_tambahan \n" +
                        "from \n" +
                        "  permintaan_radiologi \n" +
                        "  inner join reg_periksa on permintaan_radiologi.no_rawat = reg_periksa.no_rawat \n" +
                        "  inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                        "  inner join dokter on permintaan_radiologi.dokter_perujuk = dokter.kd_dokter \n" +
                        "  inner join poliklinik on reg_periksa.kd_poli = poliklinik.kd_poli \n" +
                        "  inner join penjab on reg_periksa.kd_pj = penjab.kd_pj \n" +
                        "  LEFT JOIN pemeriksaan_ralan on pemeriksaan_ralan.no_rawat = permintaan_radiologi.no_rawat \n" +
                        "  LEFT JOIN dokter drj on drj.kd_dokter = permintaan_radiologi.dokter_perujuk \n" +
                        "  inner join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder = permintaan_radiologi.noorder \n" +
                        "  inner join jns_perawatan_radiologi on permintaan_pemeriksaan_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw \n" +
                        "where \n" +
                        "  permintaan_radiologi.noorder = ?"
             );
             try {
                 ps.setString(1,nopermintaan);
                 System.out.println("ps string: "+ps.toString());
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    // Staging
                     headers.add("Authorization","Bearer 6|92yk8doUXKTI85IIOtiG0lfTsPayiA0PE7k2hwHJ"); //remote
                    
                    // Production
//                    headers.add("Authorization","Bearer 2|EZJmynMxwo8Q6wfHFu3moXYqTW5yCXVPSMGoyjJD");
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Accept","application/json");
                    headers.add("Content-Type","application/json");                   
                    
                       requestJson="{" +
                                            //"\"uid\": \""+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+"."+rs.getString("noorder").substring(2,14)+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                               "\"uid\": \""+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+""+randomNumber+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                                //"\"acc\": \""+rs.getString("noorder").substring(8,10)+rs.getString("no_rkm_medis").substring(rs.getString("no_rkm_medis").length() -3)+rs.getString("no_rawat").substring(rs.getString("no_rawat").length()-2)+rs.getString("noorder").substring(rs.getString("noorder").length() -2)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+"\","+
                                                //"\"acc\": \""+rs.getString("no_rkm_medis").substring(rs.getString("no_rkm_medis").length() -2)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -4)+rs.getString("no_rawat").substring(rs.getString("no_rawat").length()-4)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                                "\"acc\": \""+rs.getString("noorder").substring(4,14)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -4)+"\","+
                                                "\"patientid\": \""+rs.getString("no_rkm_medis")+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+"\","+
                                                "\"mrn\": \""+rs.getString("no_rkm_medis")+"\","+
                                                "\"name\": \""+rs.getString("nm_pasien")+"\","+
                                                "\"address\": \""+rs.getString("alamat")+"\","+
                                                "\"sex\": \""+rs.getString("jk").replaceAll("L","M").replaceAll("P","F")+"\","+
                                                "\"birth_date\": \""+rs.getString("tgl_lahir")+"\","+
                                                "\"weight\": \""+"0"+"\","+
                                                "\"dep_id\": \""+rs.getString("kd_poli")+"\","+
                                                "\"name_dep\": \""+rs.getString("nm_poli")+"\","+
                                                "\"id_modality\": \""+"0"+"\","+
                                                "\"xray_type_code\": \""+rs.getString("nm_perawatan").trim().substring(0,2)+"\","+
                                                "\"id_prosedur\": \""+rs.getString("kd_jenis_prw")+"\","+
                                                "\"prosedur\": \""+rs.getString("nm_perawatan")+"\","+
                                                "\"harga_prosedur\": \""+rs.getString("total_byr")+"\","+
                                                "\"radiographer_id\": \""+"kosong"+"\","+
                                                "\"radiographer_name\": \""+"kosong"+"\","+
                                                "\"dokterid\": \""+rs.getString("dokter_perujuk")+"\","+
                                                "\"named\": \""+rs.getString("perujuk")+"\","+
                                                "\"dokradid\": \""+"kosong"+"\","+
                                                "\"dokrad_name\": \""+"kosong"+"\","+  
                                                "\"create_time\": \""+rs.getString("tgreg")+" "+rs.getString("jam")+"\","+
                                                "\"schedule_date\": \""+rs.getString("tgl_sampel")+"\","+
                                                "\"schedule_time\": \""+rs.getString("jam_permintaan")+"\","+
                                                "\"priority\": \""+rs.getString("informasi_tambahan")+"\","+
                                                "\"pat_state\": \""+"Ralan"+"\","+
                                                "\"spc_needs\": \""+rs.getString("diagnosa_klinis")+"\","+
                                                "\"id_payment\": \""+rs.getString("kd_pj")+"\","+
                                                "\"payment\": \""+rs.getString("png_jawab")+"\","+
                                                "\"fromorder\": \""+"SIMRS"+"\""+
                                           "}";         
                    
                    koneksifuji=koneksiDBFUJI.condb();
                    
                    String query_dokter_radiologi = "SELECT \n" +
                                                    "  periksa_radiologi.kd_dokter, \n" +
                                                    "  dokter.nm_dokter \n" +
                                                    "FROM \n" +
                                                    "  `periksa_radiologi` \n" +
                                                    "  LEFT JOIN dokter ON periksa_radiologi.kd_dokter = dokter.kd_dokter \n" +
                                                    "WHERE \n" +
                                                    "  periksa_radiologi.no_rawat = ? AND periksa_radiologi.kd_jenis_prw = ?;";
                    
                    ps_dokter_radiologi = koneksi.prepareStatement(query_dokter_radiologi);
                    ps_dokter_radiologi.setString(1, rs.getString("no_rawat"));
                    ps_dokter_radiologi.setString(2, rs.getString("kd_jenis_prw"));
                    
                    rs_dokter_radiologi = ps_dokter_radiologi.executeQuery();
                    if (rs_dokter_radiologi.next()) {
                        nama_dokter_radiologi = rs_dokter_radiologi.getString("nm_dokter");
                    }
                    
                    String query_order_in = "insert into order_in ("
                                                    + "nama_pasien,"
                                                    + "no_register,"
                                                    + "no_rm,"
                                                    + "no_rontgen,"
                                                    + "uid,"
                                                    + "tanggal_order,"
                                                    + "tanggal_lahir,"
                                                    + "dokter_pengirim,"
                                                    + "dokter_radiolog,"
                                                    + "asal_ruangan, "
                                                    + "kode_tindakan, "
                                                    + "tindakan_radiologi, "
                                                    + "jasa_sarana, "
                                                    + "bhp, "
                                                    + "tarif_perujuk, "
                                                    + "jasa_dokter_radiolog, "
                                                    + "jasa_petugas_radiologi, "
                                                    + "kso, "
                                                    + "menejemen, "
                                                    + "total_biaya, "
                                                    + "informasi_tambahan, "
                                                    + "diagnosa_klinis, "
                                                    + "statusupdate"
                                            + ") "
                                            + "values ('"
                                                    +rs.getString("nm_pasien")
                                                    +"','"+rs.getString("no_rawat")
                                                    +"','"+rs.getString("no_rkm_medis")
                                                    +"','"+rs.getString("noorder")
                                                    +"','"+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+""+randomNumber+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)
                                                    +"','"+rs.getString("tgl_permintaan")
                                                    +"','"+rs.getString("tgl_lahir")
                                                    +"','"+rs.getString("perujuk")
                                                    +"','"+nama_dokter_radiologi
                                                    +"','"+rs.getString("nm_poli")
                                                    +"','"+rs.getString("kd_jenis_prw")
                                                    +"','"+rs.getString("nm_perawatan")
                                                    +"','"+rs.getString("bagian_rs")
                                                    +"','"+rs.getString("bhp")
                                                    +"','"+rs.getString("tarif_perujuk")
                                                    +"','"+rs.getString("tarif_tindakan_dokter")
                                                    +"','"+rs.getString("tarif_tindakan_petugas")
                                                    +"','"+rs.getString("kso")
                                                    +"','"+rs.getString("menejemen")
                                                    +"','"+rs.getString("total_byr")
                                                    +"','"+rs.getString("informasi_tambahan")
                                                    +"','"+rs.getString("diagnosa_klinis")
                                                    +"','0"
                                            +"')";
                    System.out.println("query_order_in: "+query_order_in);
                    koneksifuji.prepareStatement(query_order_in).executeUpdate();
                    
                    // kirim ke bridging radiologi untuk tracking sync balik ke khanza
                    koneksibridgingradiologi=koneksiBridgingRadiologi.condb();
                    String query_permintaan_radiologi = "insert into permintaan_radiologi ("
                                                                + "no_rawat,"
                                                                + "no_order,"
                                                                + "kd_jenis_prw"
                                                        + ") "
                                                        + "values ('"
                                                                +rs.getString("no_rawat")
                                                                +"','"+rs.getString("noorder")
                                                                +"','"+rs.getString("kd_jenis_prw")
                                                        +"')";
                    
                    koneksibridgingradiologi.prepareStatement(query_permintaan_radiologi).executeUpdate();
                    
//                    System.out.println("JSON : "+requestJson);
//                    System.out.println("URL : "+URL+"/orders");
//                    requestEntity = new HttpEntity(requestJson,headers);	    
//                    stringbalik=getRest().exchange(URL+"/orders", HttpMethod.POST, requestEntity, String.class).toString();
//                    
//                    JOptionPane.showMessageDialog(null,stringbalik);
                    
                }
             } catch (Exception e) {
                 
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
                 }
             } finally{
                 if(rs!=null){
                     rs.close();
                 }
                 if(ps!=null){
                     ps.close();
                 }
             }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
            }
        }
    }
    
    public void kirimRanap(String nopermintaan) {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        try {
             ps=koneksi.prepareStatement(
                    "select \n" +
                    "  permintaan_radiologi.noorder, \n" +
                    "  permintaan_radiologi.no_rawat, \n" +
                    "  reg_periksa.tgl_registrasi as tgreg, \n" +
                    "  reg_periksa.kd_pj, \n" +
                    "  reg_periksa.jam_reg as jam, \n" +
                    "  drj.nm_dokter as perujuk, \n" +
                    "  kamar_inap.kd_kamar, \n" +
                    "  reg_periksa.no_rkm_medis, \n" +
                    "  jns_perawatan_radiologi.total_byr, \n" +
                    "  pemeriksaan_ralan.berat, \n" +
                    "  pasien.nm_pasien, \n" +
                    "  permintaan_radiologi.tgl_permintaan, \n" +
                    "  if(\n" +
                    "    permintaan_radiologi.jam_permintaan = '00:00:00', \n" +
                    "    '', permintaan_radiologi.jam_permintaan\n" +
                    "  ) as jam_permintaan, \n" +
                    "  pasien.tgl_lahir, \n" +
                    "  pasien.jk, \n" +
                    "  pasien.alamat, \n" +
                    "  if(\n" +
                    "    permintaan_radiologi.tgl_sampel = '0000-00-00', \n" +
                    "    '', permintaan_radiologi.tgl_sampel\n" +
                    "  ) as tgl_sampel, \n" +
                    "  if(\n" +
                    "    permintaan_radiologi.jam_sampel = '00:00:00', \n" +
                    "    '', permintaan_radiologi.jam_sampel\n" +
                    "  ) as jam_sampel, \n" +
                    "  if(\n" +
                    "    permintaan_radiologi.tgl_hasil = '0000-00-00', \n" +
                    "    '', permintaan_radiologi.tgl_hasil\n" +
                    "  ) as tgl_hasil, \n" +
                    "  if(\n" +
                    "    permintaan_radiologi.jam_hasil = '00:00:00', \n" +
                    "    '', permintaan_radiologi.jam_hasil\n" +
                    "  ) as jam_hasil, \n" +
                    "  permintaan_radiologi.dokter_perujuk, \n" +
                    "  bangsal.nm_bangsal, \n" +
                    "  bangsal.kd_bangsal, \n" +
                    "  kamar_inap.kd_kamar, \n" +
                    "  pasien.no_tlp, \n" +
                    "  penjab.png_jawab, \n" +
                    "  permintaan_pemeriksaan_radiologi.kd_jenis_prw, \n" +
                    "  jns_perawatan_radiologi.nm_perawatan, \n" +
                    "  permintaan_radiologi.diagnosa_klinis, \n" +
                    "  permintaan_radiologi.informasi_tambahan \n" +
                    "from \n" +
                    "  permintaan_radiologi \n" +
                    "  inner join reg_periksa on permintaan_radiologi.no_rawat = reg_periksa.no_rawat \n" +
                    "  inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                    "  inner join dokter on permintaan_radiologi.dokter_perujuk = dokter.kd_dokter \n" +
                    "  inner join kamar_inap on kamar_inap.no_rawat = reg_periksa.no_rawat \n" +
                    "  inner join kamar on kamar_inap.kd_kamar = kamar.kd_kamar \n" +
                    "  inner join bangsal on bangsal.kd_bangsal = kamar.kd_bangsal \n" +
                    "  inner join penjab on reg_periksa.kd_pj = penjab.kd_pj \n" +
                    "  LEFT JOIN pemeriksaan_ralan on pemeriksaan_ralan.no_rawat = permintaan_radiologi.no_rawat \n" +
                    "  LEFT JOIN dokter drj on drj.kd_dokter = permintaan_radiologi.dokter_perujuk \n" +
                    "  inner join permintaan_pemeriksaan_radiologi on permintaan_pemeriksaan_radiologi.noorder = permintaan_radiologi.noorder \n" +
                    "  inner join jns_perawatan_radiologi on permintaan_pemeriksaan_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw \n" +
                    "where \n" +
                    "  permintaan_radiologi.noorder = ?");
             try {
                ps.setString(1,nopermintaan);
                rs=ps.executeQuery();
                while(rs.next()){
                    headers = new HttpHeaders();
                    
                    // Staging
                    headers.add("Authorization","Bearer 6|92yk8doUXKTI85IIOtiG0lfTsPayiA0PE7k2hwHJ"); //remote
                    
                    // Production
                    // headers.add("Authorization","Bearer 2|EZJmynMxwo8Q6wfHFu3moXYqTW5yCXVPSMGoyjJD");
                    
                    headers.add("Authorization","Bearer 2|EZJmynMxwo8Q6wfHFu3moXYqTW5yCXVPSMGoyjJD");
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Accept","application/json");
                    headers.add("Content-Type","application/json");

                    requestJson="{" +
                                            //"\"uid\": \""+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+"."+rs.getString("noorder").substring(2,14)+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                               "\"uid\": \""+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+""+randomNumber+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                                //"\"acc\": \""+rs.getString("noorder").substring(8,10)+rs.getString("no_rkm_medis").substring(rs.getString("no_rkm_medis").length() -3)+rs.getString("no_rawat").substring(rs.getString("no_rawat").length()-2)+rs.getString("noorder").substring(rs.getString("noorder").length() -2)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+"\","+
                                                //"\"acc\": \""+rs.getString("no_rkm_medis").substring(rs.getString("no_rkm_medis").length() -2)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -4)+rs.getString("no_rawat").substring(rs.getString("no_rawat").length()-4)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"\","+
                                                "\"acc\": \""+rs.getString("noorder").substring(4,14)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -4)+"\","+
                                                "\"patientid\": \""+rs.getString("no_rkm_medis")+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+"\","+
                                                "\"mrn\": \""+rs.getString("no_rkm_medis")+"\","+
                                                "\"name\": \""+rs.getString("nm_pasien")+"\","+
                                                "\"address\": \""+rs.getString("alamat")+"\","+
                                                "\"sex\": \""+rs.getString("jk").replaceAll("L","M").replaceAll("P","F")+"\","+
                                                "\"birth_date\": \""+rs.getString("tgl_lahir")+"\","+
                                                "\"weight\": \""+"0"+"\","+
                                                "\"dep_id\": \""+rs.getString("kd_bangsal")+"\","+
                                                "\"name_dep\": \""+rs.getString("nm_bangsal")+"\","+
                                                "\"id_modality\": \""+"0"+"\","+
                                                "\"xray_type_code\": \""+rs.getString("nm_perawatan").substring(0,2)+"\","+
                                                "\"id_prosedur\": \""+rs.getString("kd_jenis_prw")+"\","+
                                                "\"prosedur\": \""+rs.getString("nm_perawatan")+"\","+
                                                "\"harga_prosedur\": \""+rs.getString("total_byr")+"\","+
                                                "\"radiographer_id\": \""+"kosong"+"\","+
                                                "\"radiographer_name\": \""+"kosong"+"\","+
                                                "\"dokterid\": \""+rs.getString("dokter_perujuk")+"\","+
                                                "\"named\": \""+rs.getString("perujuk")+"\","+
                                                "\"dokradid\": \""+"kosong"+"\","+
                                                "\"dokrad_name\": \""+"kosong"+"\","+  
                                                "\"create_time\": \""+rs.getString("tgreg")+" "+rs.getString("jam")+"\","+
                                                "\"schedule_date\": \""+rs.getString("tgl_sampel")+"\","+
                                                "\"schedule_time\": \""+rs.getString("jam_permintaan")+"\","+
                                                "\"priority\": \""+rs.getString("informasi_tambahan")+"\","+
                                                "\"pat_state\": \""+"Ranap"+"\","+
                                                "\"spc_needs\": \""+rs.getString("diagnosa_klinis")+"\","+
                                                "\"id_payment\": \""+rs.getString("kd_pj")+"\","+
                                                "\"payment\": \""+rs.getString("png_jawab")+"\","+
                                                "\"fromorder\": \""+"SIMRS"+"\""+
                                           "}";  
                    
                    koneksifuji=koneksiDBFUJI.condb();
                    koneksifuji.prepareStatement(
                                    "insert into order_in (nama_pasien,no_rm,no_register,no_rontgen,uid) values ('"+rs.getString("nm_pasien")+"','"+rs.getString("no_rawat")+"','"+rs.getString("no_rkm_medis")+"','"+rs.getString("noorder")+"','"+"1.2.40.0.13.1."+rs.getString("no_rkm_medis")+""+randomNumber+"."+rs.getString("no_rawat").substring(rs.getString("no_rawat").length() -4)+rs.getString("noorder").substring(2,8)+rs.getString("kd_jenis_prw").substring(rs.getString("kd_jenis_prw").length() -3)+rs.getString("noorder").substring(rs.getString("noorder").length() -3)+"')"
                                                 ).executeUpdate();
                    
                    System.out.println("JSON : "+requestJson);
                    System.out.println("URL : "+URL+"/putOrder/");
                    requestEntity = new HttpEntity(requestJson,headers);	    
                    stringbalik=getRest().exchange(URL+"/orders", HttpMethod.POST, requestEntity, String.class).getBody();
                    JOptionPane.showMessageDialog(null,stringbalik);
                  
                    
                }
             } catch (Exception e) {
                 System.out.println("Notif : "+e);
                 if(e.toString().contains("UnknownHostException")||e.toString().contains("404")){
                    JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
                 }
             } finally{
                 if(rs!=null){
                     rs.close();
                 }
                 if(ps!=null){
                     ps.close();
                 }
             }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")||ex.toString().contains("404")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Care Stream terputus...!");
            }
        }
    }
    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
}
