# System Flow Diagram: Proses Deposit & Refund Pasien (SIMRS Khanza)

Dokumen ini menjelaskan arsitektur teknis, diagram alur sistem (*System Flow Diagram*), diagram urutan (*Sequence Diagram*), transisi status (*State Diagram*), aturan bisnis (*Business Rules*), serta pencatatan jurnal akuntansi untuk proses **Deposit** dan **Refund (Pengembalian Deposit)** pasien pada aplikasi SIMRS Khanza (khususnya terintegrasi dengan modul `DlgBilingRanap.java`, `DlgDeposit.java`, `DlgPengembalianDepositPasien.java`, dan `DlgBillingRanapV2.java`).

---

## 1. Arsitektur & Gambaran Umum Sistem

Proses Pengelolaan Deposit dan Refund Pasien di SIMRS Khanza menghubungkan modul Kasir Keuangan, Pelayanan Rawat Inap/Jalan, dan Sistem Akuntansi Otomatis (General Ledger).

```mermaid
graph TD
    classDef actorStyle fill:#b0004a,color:#ffffff,stroke:#90003b,stroke-width:2px;
    classDef uiStyle fill:#e9f7ff,color:#001c3a,stroke:#a5c8ff,stroke-width:2px;
    classDef logicStyle fill:#fff9c4,color:#1a1c1c,stroke:#fbc02d,stroke-width:2px;
    classDef dbStyle fill:#e8f5e9,color:#1b5e20,stroke:#81c784,stroke-width:2px;
    classDef accStyle fill:#f3e5f5,color:#4a148c,stroke:#ba68c8,stroke-width:2px;

    User["👤 Pasien / Penanggung Jawab"]:::actorStyle -->|1. Bayar Uang Muka| DepositUI["🖥️ DlgDeposit.java"]:::uiStyle
    Kasir["👤 Petugas Kasir / Keuangan"]:::actorStyle -->|Input Transaksi| DepositUI
    
    DepositUI -->|Save Record| DB_Deposit[("🗄️ Tabel deposit & tagihan_sadewa")]:::dbStyle
    DepositUI -->|Post Journal| JurnalAuto["⚙️ Jurnal Engine (jur.simpanJurnal)"]:::logicStyle

    JurnalAuto -->|Debet Kas / Kredit Deposit| DB_Jurnal[("🗄️ Tabel tampjurnal & jurnal")]:::accStyle

    BillingUI["🖥️ DlgBilingRanap.java / DlgBillingRanapV2.java"]:::uiStyle -->|Read Total Deposit| DB_Deposit
    BillingUI -->|Hitung Tagihan vs Deposit| LogicBilling{"⚖️ Evaluasi Deposit vs Tagihan"}:::logicStyle

    LogicBilling -->|"Deposit Kurang Dari Tagihan"| SkenarioA["Pasien Bayar Sisa Kekurangan<br>(Deposit Memotong Tagihan)"]:::logicStyle
    LogicBilling -->|"Deposit Sama Dengan Tagihan"| SkenarioB["Tagihan Lunas Sempurna<br>(Sisa Deposit = 0)"]:::logicStyle
    LogicBilling -->|"Deposit Lebih Dari Tagihan"| SkenarioC["Kelebihan Deposit<br>(Auto Refund Sisa Deposit)"]:::logicStyle

    SkenarioC -->|Auto Save| RefundUI["🖥️ DlgPengembalianDepositPasien.java / DlgBillingRanapV2"]:::uiStyle
    Kasir -->|Manual Refund Request| RefundUI

    RefundUI -->|Save Record| DB_Refund[("🗄️ Tabel pengembalian_deposit / _v2")]:::dbStyle
    RefundUI -->|Post Journal| JurnalAuto
    RefundUI -->|Cetak Bukti| CetakKwitansi["🖨️ Kwitansi Pengembalian Deposit"]:::uiStyle

    SkenarioA -->|Closing Billing| DB_Jurnal
    SkenarioB -->|Closing Billing| DB_Jurnal
    SkenarioC -->|Closing Billing| DB_Jurnal
```

---

## 2. End-to-End System Flowchart (Proses Utama)

Berikut adalah diagram alur proses sistem (*System Flowchart*) dari penerimaan deposit pasien awal, perhitungan saat pasien pulang (billing closure), hingga pengembalian sisa deposit (refund):

```mermaid
flowchart TD
    Start([Mulai Transaksi Pasien]) --> InputDeposit["Petugas Kasir Buka DlgDeposit.java"]
    InputDeposit --> CekBillingVerified{"Apakah Billing Sudah Terverifikasi / Lunas?"}
    
    CekBillingVerified -- Ya --> BlockDeposit["Gagal: Transaksi Terkunci / Billing Terverifikasi"]
    BlockDeposit --> EndFail([Selesai - Pembatalan])
    
    CekBillingVerified -- Tidak --> EntryDeposit["Input No.Rawat, Akun Bayar, Nominal Deposit & Keterangan"]
    EntryDeposit --> ValidasiInput{"Validasi Nominal Lebih Dari 0 & Akun Bayar Valid?"}
    
    ValidasiInput -- Tidak --> AlertValidasi["Tampilkan Alert Form Tidak Lengkap"]
    AlertValidasi --> EntryDeposit
    
    ValidasiInput -- Ya --> SimpanDepositDB["Simpan Record ke Tabel deposit & tagihan_sadewa"]
    SimpanDepositDB --> PostJurnalDeposit["Posting Jurnal: Debet Kas/Bank, Kredit Uang Muka Ranap"]
    PostJurnalDeposit --> CetakKwitansiDep["Cetak Kwitansi Deposit Pasien"]
    CetakKwitansiDep --> ProsesPerawatan["Pasien Menjalani Perawatan Inap / Jalan"]
    
    ProsesPerawatan --> PenutupanBilling["Penanganan Billing: DlgBilingRanap.java / DlgBillingRanapV2.java"]
    PenutupanBilling --> HitungTotal["Sistem Hitung Tagihan & Sum Deposit"]
    
    HitungTotal --> Bandingkan{"Bandingkan uangdeposit vs Total Tagihan"}
    
    Bandingkan -- "Deposit Kurang Dari Tagihan" --> Kekurangan["Hitung Kekurangan = Tagihan - Deposit"]
    Kekurangan --> PasienBayarSisa["Pasien Bayar Sisa Kekurangan via Cash / QRIS / Transfer / Piutang"]
    PasienBayarSisa --> JurnalPelunasan["Jurnal Billing: Debet Uang Muka Ranap & Kas, Kredit Pendapatan Layanan"]
    JurnalPelunasan --> CloseBilling([Billing Selesai / Lunas])

    Bandingkan -- "Deposit Sama Dengan Tagihan" --> PasienPass["Kekurangan = 0, Sisa Deposit = 0"]
    PasienPass --> JurnalPelunasan

    Bandingkan -- "Deposit Lebih Dari Tagihan" --> Kelebihan["Hitung Sisa Deposit = Deposit - Tagihan"]
    Kelebihan --> AutoRefund{"Metode Refund?"}
    
    AutoRefund -- Automatic pada Billing --> SimpanAutoRefund["Simpan ke pengembalian_deposit / pengembalian_deposit_v2"]
    AutoRefund -- Manual oleh Kasir --> KasirFormRefund["Buka DlgPengembalianDepositPasien.java"]
    KasirFormRefund --> SimpanAutoRefund
    
    SimpanAutoRefund --> JurnalRefund["Jurnal Refund: Debet Uang Muka Ranap, Kredit Kas/Bank & Sisa Uang Muka"]
    JurnalRefund --> CetakKwitansiRefund["Cetak Kwitansi Refund / Pengembalian Deposit Pasien"]
    CetakKwitansiRefund --> CloseBilling
```

---

## 3. Detailed Sequence Diagrams

### 3.1 Sequence Diagram: Penerimaan Deposit Pasien (`DlgDeposit.java`)

```mermaid
sequenceDiagram
    autonumber
    actor Kasir as Petugas Kasir
    participant Form as DlgDeposit.java
    participant Sequel as fungsi.sekuel / SQL Engine
    participant DB as MySQL Database
    participant Jurnal as fungsi.Jurnal (jur)

    Kasir->>Form: Pilih No. Rawat Pasien & Input Nominal Deposit
    Form->>Sequel: cariRegistrasi(TNoRw)
    Sequel->>DB: SELECT count(*) FROM reg_periksa WHERE no_rawat=? AND stts='Lunas'
    DB-->>Sequel: Return Count

    alt Billing Sudah Terverifikasi / Lock
        Sequel-->>Form: Count > 0
        Form-->>Kasir: Show Alert Data billing sudah terverifikasi
    else Billing Masih Open
        Sequel-->>Form: Count == 0
        Kasir->>Form: Klik BtnSimpan
        Form->>Sequel: AutoCommitFalse()
        Form->>Sequel: menyimpantf2(deposit, values...)
        Sequel->>DB: INSERT INTO deposit
        
        Form->>Form: Read cache/akunbayar.iyem
        Form->>Sequel: menyimpantf2(tampjurnal, Debet, Kredit)
        Sequel->>DB: INSERT INTO tampjurnal
        
        Form->>Jurnal: simpanJurnal(no_deposit, U, DEPOSIT PASIEN)
        Jurnal->>DB: Transfer tampjurnal -> jurnal & detailjurnal
        
        Form->>Sequel: menyimpantf2(tagihan_sadewa, values...)
        Sequel->>DB: INSERT INTO tagihan_sadewa
        
        alt Transaksi Sukses
            Form->>Sequel: Commit()
            Sequel-->>Form: Success
            Form-->>Kasir: Refresh Table & Reset Form
            Kasir->>Form: Klik Print Kwitansi
            Form->>DB: Query Kwitansi Data
            Form-->>Kasir: Tampilkan rptKwitansiDeposit.jasper
        else Terjadi Error
            Form->>Sequel: RollBack()
            Form-->>Kasir: Show Alert Terjadi kesalahan transaksi dibatalkan
        end
        Form->>Sequel: AutoCommitTrue()
    end
```

---

### 3.2 Sequence Diagram: Penggunaan Deposit & Auto-Refund pada Billing Ranap (`DlgBilingRanap.java`)

```mermaid
sequenceDiagram
    autonumber
    actor Kasir as Petugas Kasir Billing
    participant BillForm as DlgBilingRanap.java
    participant DB as MySQL Database
    participant Jurnal as fungsi.Jurnal (jur)

    Kasir->>BillForm: Buka Billing Pasien (Set No. Rawat)
    BillForm->>DB: SELECT SUM(besar_deposit) FROM deposit WHERE no_rawat=?
    DB-->>BillForm: Return total uangdeposit
    
    BillForm->>DB: Query Total Rincian Biaya Tagihan Pasien
    DB-->>BillForm: Return total tagihanppn
    
    BillForm->>BillForm: Hitung Selisih: sisadeposit = uangdeposit - tagihanppn

    Kasir->>BillForm: Klik Tombol Simpan / Closing Billing

    alt Kelebihan Deposit (uangdeposit > tagihanppn)
        BillForm->>DB: INSERT INTO tampjurnal (Sisa_Uang_Muka_Ranap, Kredit, sisadeposit)
        BillForm->>DB: INSERT INTO pengembalian_deposit (no_rawat, tanggal, nip, besar_pengembalian)
        BillForm->>Jurnal: simpanJurnal(no_rawat, U, PENGEMBALIAN DEPOSIT)
    else Deposit Memotong Tagihan (uangdeposit <= tagihanppn)
        BillForm->>DB: INSERT INTO tampjurnal (Uang_Muka_Ranap, Debet, uangdeposit)
        BillForm->>Jurnal: simpanJurnal(no_rawat, U, PELUNASAN BILLING RANAP)
    end

    BillForm->>DB: UPDATE reg_periksa SET status_bayar='Lunas'
    BillForm-->>Kasir: Billing Berhasil Disimpan & Nota Terbit
```

---

## 4. State Transition Diagram: Siklus Hidup Transaksi Deposit

Diagram berikut menjelaskan perubahan status (*State Transitions*) uang deposit pasien dari saat disetorkan hingga diselesaikan:

```mermaid
flowchart LR
    Start([Mulai]) --> Draft["Draft: Input Form Deposit"]
    Draft --> Validated["Validated: Check Billing Passed"]
    Draft --> Cancelled["Cancelled: Batal Input"]
    Validated --> Deposited["Deposited: Simpan DB & Post Jurnal"]
    Deposited --> PartialRefund["Partial Refunded: Refund Sebagian"]
    PartialRefund --> Deposited
    Deposited --> Voided["Voided: Hapus / Pembatalan Deposit"]
    Voided --> End1([Selesai Batal])
    Deposited --> SettledUnder["Settled: Deposit Kurang Dari Tagihan"]
    Deposited --> SettledExact["Settled: Deposit Pas Dengan Tagihan"]
    Deposited --> SettledOver["Settled: Deposit Lebih Dari Tagihan"]
    SettledOver --> FullRefunded["Full Refunded: Pengembalian Sisa Deposit"]
    SettledUnder --> Closed["Closed: Billing Lunas Final"]
    SettledExact --> Closed
    FullRefunded --> Closed
    Closed --> End2([Selesai Lunas])
```

---

## 5. Matriks Jurnal Akuntansi (Accounting Entries Matrix)

Pencatatan akuntansi SIMRS Khanza menggunakan metode *Double-Entry Bookkeeping* yang dicatat melalui tabel perantara `tampjurnal` lalu dibukukan ke tabel `jurnal` dan `detailjurnal`:

| No | Skenario Transaksi | Kode / Nama Rekening | Posisi | Penjelasan Fungsi Jurnal |
|---|---|---|---|---|
| **1** | **Penerimaan Deposit Pasien**<br>(`DlgDeposit.java`) | `[KodeRek Bayar]` (misal: Kas Kasir / Rek. Bank) | **DEBET** | Bertambahnya kas/bank dari setoran deposit pasien |
| | | `Uang_Muka_Ranap` (Akun Deposit Pasien) | **KREDIT** | Kewajiban RS atas titipan uang muka pasien |
| **2** | **Penggunaan Deposit pada Billing**<br>(`DlgBilingRanap.java`) | `Uang_Muka_Ranap` (Akun Deposit Pasien) | **DEBET** | Pelunasan/penurunan kewajiban deposit yang dipakai |
| | | `[KodeRek Pendapatan]` (Kamar, Tindakan, Obat, dll) | **KREDIT** | Pengakuan pendapatan pelayanan RS dari deposit |
| **3** | **Kekurangan Tagihan (Pembayaran Sisa)**<br>(`Deposit < Tagihan`) | `[KodeRek Bayar Pelunasan]` / `Piutang Pasien` | **DEBET** | Kas masuk / piutang atas sisa tagihan yang belum tertutup deposit |
| | | `[KodeRek Pendapatan]` | **KREDIT** | Pengakuan sisa pendapatan |
| **4** | **Refund Sisa Deposit / Refund Manual**<br>(`DlgPengembalianDepositPasien.java`) | `Uang_Muka_Ranap` / `Sisa_Uang_Muka_Ranap` | **DEBET** | Pengurangan akun deposit/sisa uang muka |
| | | `[KodeRek Kas/Bank Pengembalian]` | **KREDIT** | Kas/Bank keluar dikembalikan ke pasien |
| | | `[KodeRek Admin]` (Jika ada admin pada V2) | **KREDIT** | Pendapatan biaya administrasi pencairan refund |
| **5** | **Pembatalan Deposit (Void Transaksi)** | `Uang_Muka_Ranap` | **DEBET** | Membalikkan kewajiban deposit yang dibatalkan |
| | | `[KodeRek Bayar]` | **KREDIT** | Membalikkan kas/bank yang batal diterima |

---

## 6. Structure & Entity Relationship Database Schema

Berikut struktur hubungan antar tabel (*ER-Diagram*) terkait deposit dan refund di SIMRS Khanza:

```mermaid
erDiagram
    REGPERIKSA ||--o{ DEPOSIT : memiliki_deposit
    REGPERIKSA ||--o{ PENGEMBALIAN : memiliki_refund
    REGPERIKSA ||--o| NOTAINAP : memiliki_nota
    REGPERIKSA ||--o{ TAGIHANSADEWA : rincian_tagihan
    JURNAL ||--o{ DETAILJURNAL : memuat_entri

    REGPERIKSA {
        string no_rawat PK
        string no_rkm_medis
        string status_bayar
    }

    DEPOSIT {
        string no_deposit PK
        string no_rawat FK
        string tgl_deposit
        string nama_bayar
        float besar_deposit
        string nip
    }

    PENGEMBALIAN {
        string no_refund PK
        string no_rawat FK
        string tanggal
        float total_refund
        string nip
    }

    NOTAINAP {
        string no_nota PK
        string no_rawat FK
        string tanggal
        string jam
        float Uang_Muka
        float total_pasien
    }

    TAGIHANSADEWA {
        string no_nota PK
        string no_rkm_medis
        string nm_pasien
        float total_tagihan
    }

    JURNAL {
        string no_jurnal PK
        string no_bukti
        string tgl_jurnal
        string jenis
    }

    DETAILJURNAL {
        string no_jurnal FK
        string kd_rek FK
        float debet
        float kredit
    }
```

---

## 7. Lokasi File & Referensi Modul Java

- **Penerimaan Deposit**: [`src/keuangan/DlgDeposit.java`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/src/keuangan/DlgDeposit.java)
- **Billing Rawat Inap Main**: [`src/keuangan/DlgBilingRanap.java`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/src/keuangan/DlgBilingRanap.java)
- **Billing Rawat Inap V2**: [`src/keuangan/DlgBillingRanapV2.java`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/src/keuangan/DlgBillingRanapV2.java)
- **Pengembalian Deposit Legacy**: [`src/keuangan/DlgPengembalianDepositPasien.java`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/src/keuangan/DlgPengembalianDepositPasien.java)
- **Pengaturan Rekening Akun**: [`src/keuangan/DlgPengaturanRekening.java`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/src/keuangan/DlgPengaturanRekening.java)
