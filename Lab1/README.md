# Lab 1 — Bắt và Phân tích gói tin Telnet & SSH

Lab thực hành thiết lập mô hình mạng gồm 3 máy (Server, Client, Attacker), sau đó sử dụng Wireshark để bắt gói tin khi truy cập từ xa bằng **Telnet** và **SSH**, từ đó so sánh mức độ an toàn giữa hai giao thức.

🎥 Video hướng dẫn: https://youtu.be/eMxv_yzZZdA

## Mục lục

- [1. Mô hình & chuẩn bị môi trường](#1-mô-hình--chuẩn-bị-môi-trường)
- [2. Bắt gói tin khi sử dụng Telnet](#2-bắt-gói-tin-khi-sử-dụng-telnet)
- [3. Bắt gói tin khi sử dụng SSH](#3-bắt-gói-tin-khi-sử-dụng-ssh)
- [4. Câu hỏi ôn tập](#4-câu-hỏi-ôn-tập)

## 1. Mô hình & chuẩn bị môi trường

1. Đặt IP tĩnh cho 3 máy: **Server**, **Attacker**, **Client**.
2. Ping thử nghiệm giữa 3 máy để xác nhận đã kết nối được với nhau.
3. Tại máy chủ, tạo một tài khoản với:
   - `username` = Tên sinh viên
   - `password` = MSSV
   
   Ví dụ tạo bằng Command Prompt:
   ```
   net user uitlab 14520123 /add
   ```

## 2. Bắt gói tin khi sử dụng Telnet

**Bước 1 — Bật dịch vụ Telnet trên Windows Server**
- Nếu chưa có feature Telnet: `Server Manager > Features > Add Feature > Telnet Server > Install`
- Bật dịch vụ: `Start > Run > services.msc > Telnet`

**Bước 2 — Thêm user vào nhóm TelnetClients tại Server**
- `Run > lusrmgr.msc > TelnetClients Group > Add user`

**Bước 3 — Kết nối từ Client đến Server**
- Dùng Putty hoặc Command Prompt kết nối Telnet bằng tài khoản đã tạo, thực hiện các lệnh cơ bản (`dir`, `mkdir`).

**Bước 4 — Bắt gói tin tại Attacker**
- Dừng bắt gói tin trên Wireshark, tìm thông tin đăng nhập và phân tích dữ liệu trao đổi giữa Client và Server.

**Bước 5 — Đổi mật khẩu phức tạp hơn**
- Đổi mật khẩu tài khoản đã tạo thành mật khẩu > 10 ký tự, gồm chữ, số và ký tự đặc biệt, rồi thử bắt gói tin lại.

## 3. Bắt gói tin khi sử dụng SSH

**Bước 1 — Cài đặt Cygwin** tại máy chủ để thiết lập SSH server.

**Bước 2 — Cấu hình và khởi động SSH server**
- Mở Cygwin Terminal, chạy `ssh-host-config`, chọn `Yes` cho các câu hỏi cấu hình.

**Bước 3 — Bắt gói tin tại Attacker**
- Bật Wireshark để theo dõi và bắt gói tin trong phiên SSH.

## 4. Câu hỏi ôn tập

### 4.1. Khái niệm và ứng dụng của Telnet và SSH

- **Telnet**: giao thức lớp ứng dụng (cổng TCP 23 mặc định), cho phép quản trị dòng lệnh từ xa; dữ liệu truyền dưới dạng **plaintext**. Chủ yếu dùng cho thiết bị mạng đời cũ, môi trường lab cô lập hoặc kiểm tra nhanh cổng dịch vụ.
- **SSH (Secure Shell)**: giao thức quản trị từ xa an toàn (cổng TCP 22 mặc định), mã hóa toàn bộ lưu lượng. Dùng để quản trị server/thiết bị mạng, truyền file an toàn (SFTP/SCP), tạo SSH Tunneling.

### 4.2. So sánh Telnet và SSH

| Tiêu chí | Telnet | SSH |
|---|---|---|
| Cổng mặc định | TCP 23 | TCP 22 |
| Mã hóa dữ liệu | Không (Plaintext) | Có (AES, ChaCha20...) |
| Xác thực máy chủ | Không hỗ trợ | Có (Host Key Fingerprint) |
| Bảo vệ toàn vẹn | Không kiểm tra | Có (HMAC) |
| Chống nghe lén | Rất kém | Rất cao |
| Tiêu chuẩn sử dụng | Đã bị loại bỏ trong thực tế | Chuẩn bắt buộc cho quản trị hệ thống |

### 4.3. Đăng nhập SSH bằng Public Key

Ngoài mật khẩu, SSH còn hỗ trợ: SSH Key-based Authentication, Host-based Authentication, Certificate-based Authentication, Keyboard-interactive (OTP/MFA).

Các bước Demo SSH Key-based:
1. Tạo cặp khóa trên Client bằng `ssh-keygen` (Linux/CMD) hoặc PuTTYgen (Windows).
2. Sao chép Public Key vào file `~/.ssh/authorized_keys` trên Server.
3. Từ Client, mở PuTTY trỏ đến file Private Key (`.ppk`) tại `Connection > SSH > Auth > Credentials`, nhấn Open để đăng nhập không cần mật khẩu.

### 4.4. Khác biệt dưới góc độ bảo mật

| Khía cạnh | Telnet | SSH |
|---|---|---|
| Tính bí mật | Bằng 0 — dữ liệu truyền công khai | Rất cao — mã hóa đối xứng ngay sau bắt tay |
| Tính toàn vẹn | Không có — dễ bị Man-in-the-Middle | Rất cao — HMAC phát hiện thay đổi dù 1 bit |
| Xác thực | Chỉ xác thực người dùng bằng mật khẩu thô | Xác thực hai chiều (Host Key + Password/Public Key) |

### 4.5. Phân tích lưu lượng Wireshark: Telnet vs SSH

- **Phiên Telnet**: Wireshark khôi phục được 100% dữ liệu — username, password (từng ký tự gõ), câu lệnh quản trị và phản hồi từ Server.
- **Phiên SSH**: Wireshark chỉ thấy gói tin bắt tay TCP và trao đổi thuật toán (Key Exchange); phần dữ liệu còn lại hiển thị dạng Encrypted packet / SSHv2 Protocol Data, không đọc được nội dung.

### 4.6. Vì sao mật khẩu phức tạp không bảo vệ được Telnet?

Mật khẩu dài/phức tạp chỉ chống được tấn công Brute-force hoặc Dictionary Attack. Điểm yếu cốt lõi của Telnet là truyền dữ liệu dạng **plaintext**, nên dù mật khẩu phức tạp đến đâu vẫn được gửi nguyên văn trên mạng. Kẻ tấn công chỉ cần dùng chức năng **Follow TCP Stream** trên Wireshark để thấy toàn bộ mật khẩu mà không cần giải mã.

### 4.7. Metadata quan sát được trong phiên SSH và rủi ro

Dù nội dung đã mã hóa, Wireshark vẫn thấy được:
- IP nguồn/đích, cổng dịch vụ (Port 22)
- Kích thước gói tin, thời điểm gửi/nhận, tần suất gói tin
- Banner phiên bản SSH (vd: `SSH-2.0-OpenSSH_7.4p1`)

Rủi ro:
- **Traffic Analysis**: suy đoán hành vi người dùng qua kích thước/khoảng thời gian gói tin.
- **Fingerprinting**: qua banner lộ hệ điều hành/phiên bản OpenSSH → tra cứu CVE để khai thác.

### 4.8. Vai trò Host-key Fingerprint và rủi ro khi bỏ qua

- **Vai trò**: xác thực danh tính Server (Server Authentication) khi kết nối lần đầu.
- **Rủi ro**: nếu bị tấn công Man-in-the-Middle (ARP Spoofing/DNS Poisoning) mà người dùng chấp nhận Host Key giả mạo mà không đối chiếu Fingerprint, toàn bộ dữ liệu sẽ bị đánh cắp.

### 4.9. Vì sao Attacker cùng mạng không bắt được lưu lượng Unicast & giải pháp

**Nguyên nhân**: mạng dùng Switch (Lớp 2) thay vì Hub — Switch chỉ đẩy gói Unicast ra đúng cổng kết nối với Server, các cổng khác (kể cả Attacker) không nhận được.

**Kỹ thuật để quan sát được lưu lượng Unicast**:
1. **ARP Spoofing/Poisoning** — giả mạo địa chỉ MAC để ép lưu lượng chạy qua máy Attacker.
2. **MAC Flooding** — làm tràn bảng MAC của Switch, buộc Switch hoạt động như Hub.
3. **Port Mirroring (SPAN Port)** — cấu hình Switch để sao chép lưu lượng sang cổng Attacker.

### 4.10. Nguyên lý Public-key Authentication và ưu điểm

**Nguyên lý** (mã hóa bất đối xứng — Private Key ở Client, Public Key ở Server):
1. Client gửi yêu cầu đăng nhập kèm thông tin Public Key.
2. Server tạo chuỗi ngẫu nhiên (Challenge), mã hóa bằng Public Key của Client và gửi lại.
3. Client dùng Private Key giải mã Challenge, tạo chữ ký số gửi lại Server.
4. Server dùng Public Key kiểm tra chữ ký; hợp lệ thì cho đăng nhập.

**Ưu điểm so với mật khẩu**:
- Mật khẩu không bao giờ truyền qua mạng → không sợ sniffing/replay attack.
- Kháng brute-force nhờ độ dài khóa lớn (RSA 2048/4096-bit hoặc Ed25519).
- Dễ tự động hóa an toàn (Script/CI-CD) mà không cần lưu mật khẩu thô trong mã nguồn.

### 4.11. Đề xuất 3 biện pháp Hardening cho dịch vụ SSH

1. **Đổi cổng mặc định (22 → ví dụ 2222)** trong `/etc/ssh/sshd_config` → loại bỏ phần lớn các đợt quét cổng tự động từ Botnet.
2. **Vô hiệu hóa đăng nhập bằng mật khẩu**: đặt `PasswordAuthentication no`, bắt buộc `PubkeyAuthentication yes` → triệt tiêu nguy cơ Brute-force mật khẩu.
3. **Vô hiệu hóa đăng nhập Root**: đặt `PermitRootLogin no` → buộc dùng tài khoản cá nhân rồi `sudo`, giúp ghi log chính xác và hạn chế thiệt hại khi lộ tài khoản.
