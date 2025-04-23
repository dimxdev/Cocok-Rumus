# 🧠 Cocok Rumus - Game Kartu Edukasi Matematika

**Cocok Rumus** adalah game edukasi berbasis Java di mana pemain harus mencocokkan pasangan kartu berisi **rumus matematika** yang sudah ditentukan pada menu Kamus.  
Game ini dibuat menggunakan Java Swing dan dilengkapi dengan tampilan grafis, sistem level, timer, serta penyimpanan skor.

---

## 🎮 Fitur Utama

- 🎴 Permainan **cocokkan kartu rumus**
- ⏱️ Timer internal untuk mencatat durasi permainan
- 📈 Penyimpanan skor otomatis ke file `high_scores.txt`
- 💡 Dua tingkat kesulitan: `Easy (16 kartu)` dan `Hard (36 kartu)`
- 🖼️ Tampilan dialog interaktif: Menang, Keluar, dan Menu Utama
- 📚 Modul tambahan: About Us, Aturan Bermain, dan Kamus.

---

## 🚀 Cara Menjalankan

### 🟢 Opsi 1: Jalankan file `.jar` (disarankan)

```bash
java -jar CocokRumus.jar
```

> Pastikan sudah menginstall **Java Runtime Environment (JRE)**.

### 🛠️ Opsi 2: Kompilasi dari kode sumber

```bash
javac -d bin src/**/*.java
cd bin
java CocokRumus
```

---

## 🧩 Tangkapan Layar (opsional)

Jika tersedia, tambahkan screenshot tampilan game:

```markdown
![Tampilan Game](screenshots/gameplay.png)
```

---

## 🛠️ Teknologi yang Digunakan

- Java 8 atau lebih baru
- Java Swing (GUI)
- Pemrograman Berorientasi Objek (OOP)

---

## 📁 Struktur Proyek

```bash
Cocok-Rumus/
├── CocokRumus.jar         # File executable
├── src/                   # Kode sumber
│   ├── game/              # Logika dan komponen permainan
│   ├── menu/              # Menu utama & kontrol UI
│   ├── aboutus/           # Panel Tentang Kami
│   ├── rules/             # Panel Aturan Main
│   └── kamus/             # Kamus Rumus
```

---

## 👨‍💻 Pembuat

Dikembangkan oleh:

- [@dimxdev](https://github.com/dimxdev)  
- [@Alfe1n](https://github.com/Alfe1n)  

Game ini merupakan bagian dari perjalanan belajar membuat aplikasi Java berbasis GUI.

---

## 📜 Lisensi

Proyek ini dibuat untuk keperluan edukasi.  
Dilarang memperjualbelikan ulang tanpa izin.