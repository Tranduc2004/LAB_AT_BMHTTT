# Lab 3: Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin

**Bộ môn:** An toàn thông tin
**Học phần:** Thực hành An toàn thông tin (AN HTTT)
**Năm học:** 2026–2027

## Thông tin sinh viên

| Trường | Giá trị |
|---|---|
| Họ và tên | *(điền tên của bạn)* |
| MSSV | *(điền MSSV của bạn)* |
| Lớp | *(điền lớp)* |
| Lab | Lab 3 – Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin |
| Ngày thực hiện | 22/09/2026 |

---

## 1. Phiên bản môi trường

| Thành phần | Phiên bản |
|---|---|
| Hypervisor | VMware Workstation Pro |
| Hệ điều hành VM | Windows 11 25H2, Build 26200.9445 |
| Network Adapter (mặc định) | Host-only (tạm chuyển NAT khi cần tải phần mềm / TH5) |
| Python | 3.14.7 |
| Wireshark | 4.6.8 (kèm Npcap) |
| Sysmon | 15.22 |
| Autoruns / Autoruns64 | Bộ Sysinternals (tải từ download.sysinternals.com) |
| Process Explorer | 17.14 |

---

## 2. Cách dựng môi trường

1. Dựng VM VMware Workstation Pro, Network Adapter = Host-only; xác nhận phiên bản Windows bằng `winver` (H1).
2. Tạo cấu trúc thư mục `C:\LAB3` gồm `Evidence`, `Tools`, `Downloads`, `Assets`; ghi mốc thời gian bắt đầu (`start_time.txt`).
3. Tải và giải nén bộ tài sản lab (`LAB3_Threats_Assets`) vào `C:\LAB3\Downloads`.
4. Cài Python 3.14.7 và Wireshark 4.6.8 qua `winget` (cần cài bổ sung App Installer vì máy chưa có sẵn `winget`); giữ Npcap khi cài Wireshark.
5. Tải bộ Sysinternals (Sysmon, Autoruns, Process Explorer) từ `download.sysinternals.com`, giải nén vào `C:\LAB3\Tools`.
6. Ghi nhận baseline hệ thống: OS info, trạng thái Defender, Firewall, cấu hình mạng, danh sách tiến trình (`Evidence\baseline_*.txt`).

---

## 3. Các tình huống đã thực hiện

### Tình huống 1 – Xác định tài sản, lỗ hổng, mối đe dọa và rủi ro
- Lập risk register 5 tài sản (thông tin xác thực, dữ liệu LAB3, dịch vụ nghiệp vụ giả lập, email/người dùng) theo mô hình Asset → Vulnerability → Threat → Risk → Control.
- Phân loại 5 tình huống theo nguồn đe dọa: hành động vô ý, hành động cố ý, thảm họa tự nhiên, lỗi kỹ thuật, lỗi quản lý.
- **Kết quả: PASS** – đạt tối thiểu 5 tài sản/nguy cơ, 5 tình huống được phân loại kèm giải thích.

### Tình huống 2 – Kiểm thử Windows Defender với file EICAR
- Tạo file test EICAR (`eicar.com.txt`), quan sát Defender chặn/quarantine qua `Get-MpThreatDetection` và Windows Security → Protection history (Event: Threat blocked / Threat quarantined, ThreatID `2147519003`).
- Ảnh: `H4_ProtectionHistory_EICAR.png`.
- **Kết quả: PASS** – Defender phát hiện và xử lý thành công (ActionSuccess: True) ở cả 3 lần thử.

### Tình huống 3 – Authentication logging (audit logon)
- Bật audit logging (`auditpol` subcategory Logon).
- Tạo tài khoản thử nghiệm `lab3user`.
- Sinh 1 lần đăng nhập thành công (Event ID 4624) và 2 lần đăng nhập thất bại thủ công (Event ID 4625).
- Đổi mật khẩu `lab3user`, kiểm chứng: mật khẩu cũ thất bại (4625), mật khẩu mới thành công (4624).
- Ảnh: `H5_Event4625.png`.
- **Kết quả: PASS** – log xác thực đầy đủ, đúng trình tự trước/sau khi đổi mật khẩu.

### Tình huống 4.1 – Cài Sysmon và Autoruns baseline
- Cài Sysmon với cấu hình `sysmon-lab.xml`; thu baseline Autoruns (`autoruns_before.csv`).
- Xác nhận Sysmon ghi log qua Event Viewer (`Microsoft-Windows-Sysmon/Operational`), Event ID 1 (Process Create) khi mở Notepad.
- Ảnh: `H6_Sysmon_Event1.png`.
- **Kết quả: PASS**.

### Tình huống 4.2 – Persistence lành tính
- Tạo Run key `LAB3_Run_Demo` (trỏ `notepad.exe`) và Scheduled Task `LAB3_Persistence_Demo` (ghi `LAB3_TASK_OK` vào `task_ran.txt` khi đăng nhập).
- Restart VM, đăng nhập lại, xác minh Run key + task đã chạy (`task_ran.txt` chứa `LAB3_TASK_OK`).
- Quan sát bằng Autoruns (tab Logon): Entry, Description, Publisher, Image Path, Timestamp.
- Đối chiếu qua Sysmon Event ID 1/11/12/13/14 (`sysmon_persistence.txt`).
- Ảnh: `H_Autoruns_LAB3_Run_Demo.png`.
- **Kết quả: PASS**.

### Tình huống 4 (mạng) – HTTP server nội bộ và xác minh listener
- Chạy HTTP server cục bộ (`python -m http.server 8080 --bind 127.0.0.1`) ở cửa sổ PowerShell không-admin.
- Xác minh cổng lắng nghe và PID bằng `Get-NetTCPConnection` + `Get-Process`.
- Đối chiếu bằng Process Explorer (Properties: Image, Command line, Current directory, User, Parent).
- **Kết luận:** listener là dịch vụ LAB được phê duyệt (bind 127.0.0.1, command line xác định, user là chính sinh viên, có bằng chứng nguồn gốc) – không kết luận chỉ dựa vào việc "có cổng mở".
- **Kết quả: PASS**.

### Tình huống 5 – Sniffing, MITM và Spoofing: HTTP so với HTTPS
- Capture trên interface Loopback (Npcap Loopback Adapter), filter `http.request || tcp.port == 8080`.
- Gửi HTTP GET chứa chuỗi huấn luyện (`lab_user=lab3_student&lab_code=TRAINING_ONLY`) → Wireshark đọc được toàn bộ Request URI dạng plaintext.
- Ảnh: `H9_HTTP_Plaintext.png`.
- Chuyển VM sang NAT tạm thời, capture trên Ethernet0, filter `tls || tcp.port == 443`, gửi HTTPS request tới `example.com` → chỉ thấy metadata (IP, cổng, TLS handshake), không đọc được nội dung (Application Data đã mã hóa).
- Ảnh: `H10_TLS_443.png`.
- Không thực hiện ARP poisoning, DNS spoofing, Wi-Fi giả mạo, session hijacking hay chèn chứng chỉ.
- **Kết quả: PASS** – phân biệt rõ những gì quan sát được (metadata) và không quan sát được (payload) khi dùng HTTPS; nêu rõ giới hạn: mã hóa đường truyền không giải quyết phishing/endpoint compromise/người dùng tự cung cấp thông tin.

### Tình huống 6 – DoS, DDoS và Mail Bombing (offline/mô phỏng có giới hạn)
- **6.1 DoS:** chạy script tải cục bộ giới hạn (50 request, 5 worker) chỉ nhắm `127.0.0.1:8080`; ghi nhận requests/workers/ok-failures/elapsed/avg_latency (`local_load_test.txt`).
- **6.2 DDoS:** phân tích dataset tĩnh `ddos_sample.csv` (dải TEST-NET), đếm số request theo SourceIP (`ddos_sources.txt`); so sánh "một nguồn tạo tải" (TH6.1) với "nhiều SourceIP phân tán" (dataset) → giải thích vì sao chặn 1 IP không đủ để chống DDoS.
- **6.3 Mail bombing:** phân tích log offline `mailbomb_sample.csv`, không gửi email thật; phát hiện `bulk-sender@example.invalid` gửi 60/80 email (75%), cao bất thường so với mức nền ~5 email/sender.
- Ảnh: `H10_Load_and_Log_Analysis.png`.
- **Kết quả: PASS** – không có traffic gây tải ra ngoài; phân biệt được DoS/DDoS; xác định sender/volume bất thường.

### Tình huống 7 – Social Engineering, Phishing và Spear Phishing (offline)
- Mở mẫu email huấn luyện (`phishing_email.txt`), đánh dấu tối thiểu 5 chỉ dấu phishing: cảm giác khẩn cấp, display name đáng ngờ, domain cần xác minh, Reply-To khác From, yêu cầu truy cập link/cung cấp thông tin xác thực.
- Phân loại 6 case trong `social_engineering_cases.csv` (SE01–SE06): Phishing, Spear Phishing, Pretexting/Vishing, Baiting, Quid Pro Quo, Watering Hole.
- Ảnh: `H10_Phishing_Offline.png`.
- **Kết quả: PASS** – nhận diện tối thiểu 5 chỉ dấu, phân loại đúng 6 case, kèm biện pháp phòng tránh.

### Bước 8 – Cô lập, cleanup, phục hồi và kiểm tra lại
- Xóa Run key `LAB3_Run_Demo`, Scheduled Task `LAB3_Persistence_Demo`.
- Dừng HTTP server (kill process theo PID đang listen cổng 8080).
- Xóa tài khoản `lab3user`.
- Thu Autoruns sau cleanup (`autoruns_after.csv`), so sánh với baseline trước (`autoruns_diff.txt`).
- Tính SHA-256 cho toàn bộ file trong `Evidence` (`evidence_sha256.csv`).
- Kiểm tra lại: không còn Run key/Scheduled Task/listener 8080; Defender vẫn bật (`AntivirusEnabled/RealTimeProtectionEnabled/IsTamperProtected` = True).
- Ảnh: `H11_Recovery_Verification.png`.
- Revert VM về snapshot sạch sau khi đã lưu trữ đầy đủ bằng chứng ra ngoài VM.
- **Kết quả: PASS** – artefact đã loại bỏ hoàn toàn; endpoint protection vẫn hoạt động; bằng chứng đã hash.

---

## 4. Bảng tổng hợp kết quả

| Tình huống | Nội dung | Kết quả |
|---|---|---|
| TH1 | Risk register + phân loại nguồn đe dọa | PASS |
| TH2 | Kiểm thử EICAR / Defender | PASS |
| TH3 | Authentication logging (4624/4625) | PASS |
| TH4.1 | Sysmon + Autoruns baseline | PASS |
| TH4.2 | Persistence lành tính | PASS |
| TH4 (mạng) | HTTP server + xác minh listener | PASS |
| TH5 | Sniffing HTTP vs HTTPS | PASS |
| TH6 | DoS/DDoS/Mail bombing (mô phỏng offline) | PASS |
| TH7 | Social Engineering / Phishing | PASS |
| Bước 8 | Cleanup, hash evidence, phục hồi | PASS |

---

## 5. Lỗi gặp phải và cách khắc phục

| # | Lỗi gặp phải | Nguyên nhân | Cách khắc phục |
|---|---|---|---|
| 1 | `Expand-Archive`/`Get-FileHash` báo "path does not exist" khi thao tác với `LAB3_Threats_Assets.zip` | File tải về đã tự động được giải nén sẵn (không còn file .zip), chỉ còn thư mục | Kiểm tra bằng `Get-ChildItem -Force`, dùng trực tiếp thư mục đã giải nén thay vì `Expand-Archive` |
| 2 | `winget` báo "not recognized" | Máy chưa cài App Installer (winget chưa có sẵn) | Cài App Installer qua Microsoft Store (VM đang NAT nên có Internet), mở lại PowerShell mới sau khi cài |
| 3 | `Set-Content` tạo file EICAR báo lỗi "Missing an argument for parameter 'Encoding'" | Dán nhiều dòng lệnh nối bằng backtick bị "trôi dòng" khi paste vào console | Chạy từng dòng lệnh riêng biệt, không dán khối nhiều dòng cùng lúc |
| 4 | Windows Security → Protection history hiển thị "No recent actions" dù đã tạo EICAR | File EICAR chưa từng được ghi thành công do lỗi cú pháp ở trên | Sửa lỗi cú pháp, tạo lại file; xác nhận qua `Get-MpThreatDetection` trước khi kiểm tra UI |
| 5 | `runas /user:.\lab3user cmd.exe` báo "RUNAS ERROR: Unable to acquire user password" liên tục | Lỗi tầng console đọc input mật khẩu ẩn (không phải sai mật khẩu) | Dùng `Get-Credential` (hộp thoại GUI) kết hợp `Start-Process -Credential` thay cho `runas` trong Command Prompt |
| 6 | `Start-Process -Credential ... -Verb RunAs` báo "Parameter set cannot be resolved" | `-Credential` và `-Verb RunAs` thuộc 2 parameter set khác nhau, không dùng chung được | Bỏ `-Verb RunAs`, chỉ giữ `-Credential` |
| 7 | Cửa sổ cmd mở bằng `Start-Process -Credential` không nhận input khi gõ | Tiến trình chạy dưới session/desktop khác với tài khoản admin hiện tại | Không cần thao tác trong cửa sổ đó; xác nhận đăng nhập thành công qua Event Viewer (Event ID 4624) thay vì qua giao diện |
| 8 | Autoruns không hiển thị `LAB3_Run_Demo` dù Registry đã có key | (a) Autoruns hiển thị dữ liệu cũ chưa refresh; (b) mục bị untick checkbox sau khi thao tác tìm kiếm | Nhấn F5 để refresh; dùng Search (Handle/DLL substring) để định vị nhanh; tick lại checkbox nếu bị bỏ chọn nhầm |
| 9 | Wireshark không hiển thị bất kỳ interface nào để capture (kể cả Loopback, Ethernet) | Npcap chưa được cài do `winget install Wireshark` chạy ở chế độ silent, bỏ qua bước cài Npcap kèm theo | Tải và cài Npcap thủ công từ trang chủ (npcap.com), sau đó mở lại Wireshark |
| 10 | `Export-Csv` báo "cannot be read: being used by another process" khi tính hash cho toàn bộ thư mục Evidence | Pipeline vừa đọc (`Get-ChildItem`) vừa ghi (`Export-Csv`) vào cùng thư mục, xung đột khóa file với chính file output | Thêm `Where-Object { $_.Name -ne 'evidence_sha256.csv' }` để loại trừ file output khỏi danh sách quét trước khi hash |
| 11 | Nhiều lệnh nhiều dòng nối bằng backtick (`` ` ``) bị lỗi cú pháp khi copy/paste (thiếu dấu `-`, dòng bị "nuốt" ký tự) | Backtick line-continuation trong PowerShell dễ bị lỗi khi dán qua console, đặc biệt nếu tài liệu gốc có ký tự ẩn/dính chữ khác | Ưu tiên tách lệnh thành nhiều dòng độc lập, chạy từng dòng một; hoặc dùng `|` ở cuối dòng thay cho backtick khi có thể |

---

## 6. Danh sách bằng chứng (Evidence)

```
C:\LAB3\Evidence\
├── start_time.txt
├── baseline_os.txt
├── baseline_defender.txt
├── baseline_firewall.txt
├── baseline_network.txt
├── baseline_processes.txt
├── eicar.com.txt (đã bị Defender xử lý)
├── defender_eicar.txt
├── logon_events.txt
├── failed_logon_events.txt
├── password_rotation_verify.txt
├── auth_events_before_rotation.txt
├── autoruns_before.csv
├── autoruns_after.csv
├── autoruns_diff.txt
├── sysmon_persistence.txt
├── task_ran.txt
├── local_load_test.txt
├── ddos_sources.txt
├── mail_sender_counts.txt
├── mail_volume.txt
├── evidence_sha256.csv
└── (các ảnh chụp màn hình H1–H11)
```

## 7. Kết luận chung

Qua bài lab, sinh viên đã thực hành xuyên suốt chu trình: xác định tài sản/rủi ro → mô phỏng và quan sát các mối đe dọa (malware test, brute-force logon, persistence, sniffing, DoS/DDoS/mail bombing, social engineering) → thu thập bằng chứng có kiểm soát → cleanup và xác minh khôi phục. Toàn bộ hoạt động được thực hiện trong phạm vi VM lab, không gây ảnh hưởng ra ngoài, không sử dụng dữ liệu/tài khoản thật, và bằng chứng được đảm bảo toàn vẹn bằng SHA-256.
