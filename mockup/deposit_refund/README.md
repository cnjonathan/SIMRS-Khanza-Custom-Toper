# Documentation & Mockup: Deposit & Refund Pasien (SIMRS Khanza)

Folder ini berisi dokumentasi teknis, diagram alur sistem (*System Flow Diagrams*), serta mockup antarmuka pengguna (UI) HTML untuk pengelolaan **Deposit (Uang Muka)** dan **Refund (Pengembalian Deposit)** Pasien di SIMRS Khanza.

---

## 📁 Daftar File & Komponen

| File / Component | Tipe | Deskripsi & Fungsi Utama |
|---|---|---|
| 🌐 [`system_flow_viewer.html`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/mockup/deposit_refund/system_flow_viewer.html) | **Interactive HTML Viewer** | Interactive web dashboard penyaji diagram flowchart, sequence diagram, state transition diagram, matriks jurnal akuntansi, dan ERD database secara visual menggunakan Mermaid.js. |
| 📄 [`system_flow_deposit_refund.md`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/mockup/deposit_refund/system_flow_deposit_refund.md) | **Technical Spec (Markdown)** | Dokumen spesifikasi teknis lengkap yang menjelaskan arsitektur, algoritma perhitungan deposit vs tagihan di `DlgBilingRanap.java`, urutan eksekusi SQL, serta aturan akuntansi jurnal otomatis. |
| 💻 [`deposit.html`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/mockup/deposit_refund/deposit.html) | **UI Mockup HTML** | Mockup antarmuka Form Input Deposit / Titipan Pasien (berdasarkan `DlgDeposit.java`). |
| 💸 [`refund.html`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/mockup/deposit_refund/refund.html) | **UI Mockup HTML** | Mockup antarmuka Form Pengembalian Deposit Pasien (berdasarkan `DlgPengembalianDepositPasien.java` & `DlgBillingRanapV2.java`). |
| 📊 [`mutasi.html`](file:///C:/Users/Christopher%20Jonathan/Documents/Project/SIMRSKhanza%20Custom%20Toper/mockup/deposit_refund/mutasi.html) | **UI Mockup HTML** | Mockup antarmuka Laporan Mutasi & History Deposit Pasien. |

---

## 🔄 Ringkasan Alur Sistem (Summary Flow)

1. **Penerimaan Deposit (`DlgDeposit.java`)**:
   - Kasir menerima uang deposit pasien -> Disimpan ke tabel `deposit` & `tagihan_sadewa`.
   - Post Jurnal Automatic: **Debet Kas/Bank** & **Kredit Uang_Muka_Ranap**.
2. **Perhitungan Billing Ranap (`DlgBilingRanap.java`)**:
   - Total Deposit = `SUM(deposit.besar_deposit)`.
   - **Skenario A (Deposit < Tagihan)**: Deposit memotong tagihan, sisa kekurangan dibayar pasien.
   - **Skenario B (Deposit > Tagihan)**: Sisa kelebihan deposit diproses **Auto Refund** ke tabel `pengembalian_deposit` / `pengembalian_deposit_v2`.
   - **Skenario C (Deposit == Tagihan)**: Tagihan lunas sempurna oleh deposit.
3. **Pengembalian Deposit / Refund (`DlgPengembalianDepositPasien.java`)**:
   - Refund manual atau otomatis disimpankan ke `pengembalian_deposit` / `_v2`.
   - Post Jurnal Automatic: **Debet Uang_Muka_Ranap** & **Kredit Kas/Bank** (+ Biaya Admin jika ada).
4. **Pembatalan Deposit (Void Transaksi)**:
   - Menghapus record deposit/refund dan memposting Jurnal Reversal (Pembalikan).

---

## 🚀 Cara Menggunakan Mockup & Viewer

1. Buka `system_flow_viewer.html` pada browser untuk melihat diagram interaktif secara visual.
2. Gunakan tab navigasi untuk berpindah antara **System Flowchart**, **Sequence Diagrams**, **State Transitions**, **Matriks Jurnal**, dan **Skema Database (ERD)**.
3. Buka `deposit.html`, `refund.html`, dan `mutasi.html` untuk meninjau mockup antarmuka Java Swing Khanza yang diselaraskan dengan Tailwind CSS.
