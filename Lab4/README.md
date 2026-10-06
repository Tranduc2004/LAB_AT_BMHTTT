# LAB 4: Nmap - Rà soát an toàn mạng nội bộ

## 1. Thông tin sinh viên

| Mục | Nội dung |
|---|---|
| Họ tên: Tran Duc|
| MSSV | 1150080131 |
| Tên lab | LAB4 - Nmap (Tài liệu thực hành An toàn hệ thống thông tin) |
| Ngày thực hiện | 29/09/2026 |

> Toàn bộ quá trình quét chỉ thực hiện trên các máy ảo của chính tôi trong mạng Host-only. Không quét mạng Wi-Fi/Ethernet thật.

## 2. Phiên bản môi trường

| Thành phần | Phiên bản / cấu hình |
|---|---|
| Phần mềm ảo hóa | VMware Workstation [ĐIỀN PHIÊN BẢN] (tài liệu gốc viết cho VirtualBox) |
| Máy thật (Host) | Windows [ĐIỀN 10/11], adapter VMnet1 |
| Kali Linux (máy quét) | Kali 2026.2 (cài từ ISO installer), guest OS chọn Debian 12.x 64-bit, 2 GB RAM, 2 CPU, đĩa 20 GB |
| Nmap | 7.99 (gói 7.99+dfsg-1kali1) |
| Zenmap | 7.99+dfsg-1kali1 (đã có sẵn, không dùng cho chấm điểm) |
| Metasploitable 2 (máy đích) | Linux 2.6.24-16-server (Ubuntu), 512 MB RAM, 1 CPU, đĩa 8 GB |
| Windows VM (máy đích đối chiếu) | Windows 10 x64, hostname DESKTOP-U06J4P4 |

## 3. Cách dựng môi trường

### 3.1. Mạng Host-only (VMware)

- Mở **Edit → Virtual Network Editor**, dùng mạng sẵn có **VMnet1 (Host-only)**.
- Dải mạng: **192.168.178.0/24**, DHCP bật (khác với 192.168.56.0/24 trong tài liệu gốc do VMware tự chọn).
- VMnet8 (NAT, 192.168.64.0/24) chỉ dùng tạm cho Kali khi cài đặt và cập nhật, không dùng để quét.

### 3.2. Các máy ảo

1. **Kali Linux:** tạo VM mới từ ISO, chọn Debian 12.x 64-bit, phân vùng "Guided - use entire disk / All files in one partition", giữ nguyên bộ phần mềm mặc định (Xfce, top10, default), cài GRUB lên `/dev/sda`. Sau khi cài và kiểm tra Nmap xong, đổi card mạng sang **Host-only**.
2. **Metasploitable 2:** tải file zip từ nguồn chính thức của Rapid7/SourceForge, giải nén, mở file `.vmx` bằng VMware, chọn "I copied it". Card mạng duy nhất để **Host-only (VMnet1)**, không dùng Bridged. Đăng nhập bằng tài khoản mặc định của lab.
3. **Windows VM:** dùng VM Windows 10 sẵn có, card mạng Host-only.
4. Tạo snapshot **"Before-LAB4"** cho từng VM trước khi thực hành.

### 3.3. Địa chỉ IP thực tế (bảng mục 4.2)

| Thiết bị | IP thực tế | Subnet | Ghi chú |
|---|---|---|---|
| Windows Host | 192.168.178.1 | 255.255.255.0 | VMware Network Adapter VMnet1 |
| Kali VM | 192.168.178.128 | 255.255.255.0 | Máy quét |
| Windows VM | 192.168.178.129 | 255.255.255.0 | Máy đích đối chiếu (MAC 00-0C-29-A9-6F-0C) |
| Metasploitable 2 | 192.168.178.130 | 255.255.255.0 | Máy đích (MAC 00:0C:29:73:85:18) |

Kiểm tra kết nối trước khi quét: `ping -c 4 192.168.178.130` từ Kali. [ĐIỀN KẾT QUẢ: thành công/thất bại]

## 4. Các tình huống đã thực hiện và kết quả

Quy ước: **PASS** = chạy đúng lệnh theo yêu cầu, có kết quả và minh chứng khớp yêu cầu mục tương ứng. **CHƯA** = chưa có minh chứng. Mọi lệnh quét đều thêm `-n` (tắt phân giải DNS) vì Kali đã ngắt NAT, không có DNS (xem mục 5).

### 4.1. Nhiệm vụ 1 - Phát hiện host (mục 5.1)

Lệnh: `sudo nmap -sn -n 192.168.178.0/24` → **5 host up**, quét xong trong 1.93 giây.

| IP | MAC / Vendor | Vai trò | Bằng chứng |
|---|---|---|---|
| 192.168.178.1 | 00:50:56:C0:00:01 (VMware) | Adapter VMnet1 của máy thật | `ipconfig` trên Windows Host |
| 192.168.178.128 | (không hiện, máy quét) | Kali | `ip -br addr` trên Kali |
| 192.168.178.129 | 00:0C:29:A9:6F:0C (VMware) | Windows VM | `ipconfig /all`: IP và Physical Address trùng khớp |
| 192.168.178.130 | 00:0C:29:73:85:18 (VMware) | Metasploitable 2 | `ifconfig`: HWaddr trùng khớp |
| 192.168.178.254 | 00:50:56:FD:C6:C9 (VMware) | Dịch vụ DHCP của VMware | `ipconfig /all` trên Windows VM: DHCP Server = 192.168.178.254 |

Đối chiếu: 3 VM đang bật + 2 host hạ tầng (adapter máy thật và DHCP) = 5 host up. Kết quả: **PASS**.

### 4.2. Khảo sát cổng TCP (mục 6)

**Metasploitable 2 (192.168.178.130):**

| Kỹ thuật | Lệnh | Open | Closed | Filtered / Khác | Thời gian | Kết quả |
|---|---|---|---|---|---|---|
| TCP Connect | `nmap -sT -n` | 23 | 977 (conn-refused) | 0 | [CHƯA GHI] | PASS (thiếu thời gian) |
| SYN | `sudo nmap -sS -n` | 23 | 977 (reset) | 0 | 0.25 s | PASS |
| FIN | `sudo nmap -sF -n` | 0 | 977 (reset) | 23 open\|filtered | 1.48 s | PASS |
| Xmas | `sudo nmap -sX -n` | 0 | 977 (reset) | 23 open\|filtered | 1.39 s | PASS |
| NULL | `sudo nmap -sN -n` | 0 | 977 (reset) | 23 open\|filtered | 1.48 s | PASS |
| ACK | `sudo nmap -sA -n` | - | - | 1000 unfiltered (reset) | 0.24 s | PASS |

**Windows VM (192.168.178.129):**

| Kỹ thuật | Kết quả | Thời gian | Kết quả lab |
|---|---|---|---|
| SYN (-sS) | 1000 filtered (no-response) | 21.26 s | PASS |
| FIN (-sF) | 1000 open\|filtered (no-response) | 21.27 s | PASS |
| Xmas (-sX) | 1000 open\|filtered (no-response) | 21.27 s | PASS |
| NULL (-sN) | [CHƯA CHẠY] | - | CHƯA |
| ACK (-sA) | 1000 filtered (no-response) | 21.28 s | PASS |

**Nhận xét:**
- `-sT` dùng `connect()` của hệ điều hành nên không cần quyền root; `-sS` tự tạo gói SYN thô nên cần `sudo`. Hai kỹ thuật cho cùng kết quả trên Metasploitable 2 (23 open, 977 closed), chỉ khác lý do báo closed (conn-refused so với reset).
- Metasploitable 2 (Linux, không có tường lửa) trả RST cho cổng đóng nên FIN/Xmas/NULL phân biệt được closed; 23 cổng open|filtered trùng đúng với 23 cổng open của `-sS`. Trạng thái open|filtered **không** đồng nghĩa với open; kết luận mở chỉ có được nhờ đối chiếu với `-sS`.
- Windows VM im lặng với mọi kỹ thuật (SYN, FIN, Xmas, ACK đều không phản hồi), nhiều khả năng do tường lửa chặn. Đây là suy luận từ kết quả quét; [CHƯA kiểm chứng bằng cấu hình Windows Firewall].
- ACK scan trên Metasploitable 2 báo toàn bộ unfiltered: không tách được open và closed, chỉ cho biết gói ACK không bị bộ lọc chặn.

### 4.3. Quét UDP có kiểm soát (mục 7)

Lệnh: `sudo nmap -sU --top-ports 20 -n 192.168.178.130` → 18.35 giây.

| Trạng thái | Số cổng | Các cổng |
|---|---|---|
| open | 2 | 53 (domain), 137 (netbios-ns) |
| open\|filtered | 3 | 68 (dhcpc), 69 (tftp), 138 (netbios-dgm) |
| closed | 15 | 67, 123, 135, 139, 161, 162, 445, 500, 514, 520, 631, 1434, 1900, 4500, 49152 |

Nhận xét: cổng closed được xác định nhờ ICMP port unreachable; open|filtered là do không có phản hồi nên không được ghi là open. UDP chậm hơn TCP rõ rệt (18.35 s cho 20 cổng so với 0.25 s cho 1000 cổng của `-sS`). Quét UDP trên Windows VM: [CHƯA CHẠY]. Kết quả: **PASS**.

### 4.4. Dò phiên bản, hệ điều hành, quét tổng hợp (mục 8)

| Lệnh | Thời gian | Thông tin thu được |
|---|---|---|
| `sudo nmap -sV -n 192.168.178.130` | 11.95 s | Phiên bản dịch vụ của 23 cổng open (vsftpd 2.3.4, OpenSSH 4.7p1, Apache httpd 2.2.8, MySQL 5.0.51a...) |
| `sudo nmap -O -n 192.168.178.130` | [CHƯA CHẠY] | - |
| `sudo nmap -A -n 192.168.178.130` | 22.35 s | -sV + OS detection (Linux 2.6.9 - 2.6.33) + traceroute (1 hop) + default scripts |

Nhận xét: `-sV` sửa lại các tên dịch vụ đoán theo số cổng (8180: unknown → Apache Tomcat/Coyote JSP engine 1.1; 1524: ingreslock → Metasploitable root shell). `-A` mất gần gấp đôi thời gian `-sV` và tạo nhiều kết nối thật hơn (script FTP ghi nhận kết nối từ 192.168.178.128, IRC ghi nhận "Closing Link"), nên để lại nhiều dấu vết trong nhật ký máy đích và dễ bị IDS phát hiện hơn. Kết quả: **PASS** (thiếu `-O` riêng).

### 4.5. Thu thập thông tin SMB và kiểm tra MS17-010 (mục 9)

| Mục tiêu | 445/tcp | Kết quả script | Kết luận | Biện pháp phòng thủ |
|---|---|---|---|---|
| Metasploitable 2 | open (microsoft-ds) | `smb-os-discovery`: Unix (Samba 3.0.20-Debian), tên máy metasploitable, domain localdomain. `smb-vuln-ms17-010`: không báo VULNERABLE | Không ghi nhận dấu hiệu dễ bị ảnh hưởng; không khẳng định đã vá | Nâng cấp Samba, bật ký SMB (message_signing đang disabled), giới hạn cổng 445 bằng tường lửa |
| Windows VM | [CHƯA CHẠY] | [CHƯA CHẠY] | [CHƯA CHẠY] | Giữ tường lửa chặn 445 từ mạng không tin cậy, tắt SMBv1, cập nhật bản vá |

Kết quả Metasploitable 2: **PASS**. Windows VM: **CHƯA**.

## 5. Lỗi gặp phải và cách khắc phục

| # | Lỗi / Hiện tượng | Nguyên nhân | Cách khắc phục |
|---|---|---|---|
| 1 | Tài liệu viết cho VirtualBox, dải 192.168.56.0/24 | Môi trường thực tế dùng VMware | Dùng VMnet1 (Host-only), dải 192.168.178.0/24; thay IP trong mọi lệnh bằng IP thật |
| 2 | Chọn nhầm guest OS "Ubuntu" cho Kali | Kali dựa trên Debian | Chọn Debian 12.x 64-bit |
| 3 | Kali VM treo ở màn hình `Network boot from Intel E1000 ... DHCP....` | Ổ đĩa chưa có bộ khởi động, máy chuyển sang boot qua mạng | Gắn lại ISO, cài lại; ở bước GRUB chọn Yes và cài lên `/dev/sda`; chờ đến "Installation complete" rồi mới khởi động lại |
| 4 | Metasploitable 2 nhận IP 192.168.64.131 (dải NAT) | Card đầu tiên mặc định là NAT, card Host-only là eth1 chưa được cấu hình trong hệ điều hành | Đổi Network Adapter đầu tiên sang Host-only, gỡ Network Adapter 2 → IP 192.168.178.130 |
| 5 | `apt update` báo `File has unexpected size ... Mirror sync in progress?` rồi `Temporary failure resolving` | Mirror Kali đang đồng bộ; sau đó Kali đã ngắt NAT nên không có DNS | Bỏ qua vì Nmap đã có sẵn (7.99); chỉ cập nhật khi có NAT tạm thời |
| 6 | `Unable to locate package namp` | Gõ nhầm tên gói | Gõ lại đúng `nmap` |
| 7 | `ping: invalid argument: '192.168.178.130'` | Thiếu số sau `-c` | Gõ đầy đủ `-c 4` trước địa chỉ IP |
| 8 | `nmap -sn` đứng ở `Parallel DNS resolution of 4 hosts` hơn 4 phút | Nmap chờ DNS nhưng Kali không có Internet/DNS | Ctrl+C rồi chạy lại với `-n`; dùng `-n` cho mọi lệnh sau |
| 9 | `WARNING: No targets were specified, so 0 hosts scanned` | Quên gõ IP đích ở cuối lệnh `smb-os-discovery` | Gõ lại lệnh có IP đích |
| 10 | Số host up (5) nhiều hơn số VM đang bật (3) | Adapter VMnet1 của máy thật (.1) và dịch vụ DHCP của VMware (.254) | Xác định bằng tiền tố MAC `00:50:56` (hạ tầng VMware) so với `00:0C:29` (card VM) và `ipconfig /all` (DHCP Server = .254) |
| 11 | Windows VM (.129) báo toàn bộ cổng filtered, quét mất ~21 giây/lần | Nghi tường lửa chặn im lặng mọi gói thăm dò | Ghi nhận là kết quả hợp lệ; không suy diễn là "đã vá"/"an toàn" |

## 6. Việc chưa hoàn tất

- Thời gian quét `-sT` trên Metasploitable 2 (chạy lại và ghi dòng `Nmap done`).
- `-sN` trên Windows VM; `-sU --top-ports 20` trên Windows VM.
- `-O` riêng lẻ để so sánh thời gian với `-sV` và `-A`.
- Mục 9.1 và 9.2 trên Windows VM (445/tcp).
- Ảnh minh chứng `nmap --version`, ping Kali → Metasploitable 2, snapshot "Before-LAB4".

## 7. Kết luận

Môi trường lab gồm Kali, Metasploitable 2 và Windows VM trong mạng Host-only 192.168.178.0/24 đã dựng thành công. Các nhiệm vụ host discovery, quét cổng TCP/UDP, dò dịch vụ, quét tổng hợp và kiểm tra SMB trên Metasploitable 2 đã hoàn thành (PASS). Một số bước đối chiếu trên Windows VM còn thiếu như liệt kê ở mục 6.
