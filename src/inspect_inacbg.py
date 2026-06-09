import mysql.connector

try:
    conn = mysql.connector.connect(
        host="127.0.0.1",
        port=3307,
        user="root",
        password="root",
        database="khanza_emerge"
    )
    cursor = conn.cursor(dictionary=True)
    
    # Query resume_idrg for INACBG values
    cursor.execute("""
        SELECT id_resume_idrg, no_rawat, group_inacbg_code, 
               special_procedure, special_procedure_code, special_procedure_tarif,
               special_prosthesis, special_prosthesis_code, special_prosthesis_tarif,
               special_investigation, special_investigation_code, special_investigation_tarif,
               special_drug, special_drug_code, special_drug_tarif
        FROM resume_idrg
        WHERE (special_procedure IS NOT NULL AND special_procedure != 'None' AND special_procedure != '') OR
              (special_prosthesis IS NOT NULL AND special_prosthesis != 'None' AND special_prosthesis != '') OR
              (special_investigation IS NOT NULL AND special_investigation != 'None' AND special_investigation != '') OR
              (special_drug IS NOT NULL AND special_drug != 'None' AND special_drug != '')
        LIMIT 10
    """)
    rows = cursor.fetchall()
    
    for row in rows:
        print(f"ID: {row['id_resume_idrg']} | No Rawat: {row['no_rawat']} | INACBG Code: {row['group_inacbg_code']}")
        print(f"  Proc:  {repr(row['special_procedure'])} | Code: {repr(row['special_procedure_code'])} | Tarif: {row['special_procedure_tarif']}")
        print(f"  Prost: {repr(row['special_prosthesis'])} | Code: {repr(row['special_prosthesis_code'])} | Tarif: {row['special_prosthesis_tarif']}")
        print(f"  Invest:{repr(row['special_investigation'])} | Code: {repr(row['special_investigation_code'])} | Tarif: {row['special_investigation_tarif']}")
        print(f"  Drug:  {repr(row['special_drug'])} | Code: {repr(row['special_drug_code'])} | Tarif: {row['special_drug_tarif']}")
        
        # Query special_cmg_option_inacbg
        cursor.execute("SELECT type, code_cmg, description_cmg FROM special_cmg_option_inacbg WHERE id_resume_idrg = %s", (row['id_resume_idrg'],))
        options = cursor.fetchall()
        print("  Options stored in special_cmg_option_inacbg:")
        for opt in options:
            print(f"    Type: {opt['type']} | Code: {opt['code_cmg']} | Desc: {repr(opt['description_cmg'])}")
        print("-" * 50)
            
    cursor.close()
    conn.close()
except Exception as e:
    print("Error:", e)
