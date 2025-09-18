# MTSMS 系統文件總覽

## 文件說明
此目錄包含 **台灣移動電信簡訊閘道系統（MTSMS）** 的完整技術文件，幫助開發者和維護人員快速理解系統架構與運作原理。

---

## 文件列表

### 01_DataFlow_Analysis.md  
**SMS 系統資料流程分析**  
基於 `DataFlow.png` 圖表的詳細流程說明文件，涵蓋：  
- 6 個主要處理步驟的詳細分析  
- 每個步驟對應的程式檔案  
- 資料結構和控制檔案機制  
- 錯誤處理與效能優化設計  
- 監控維護與安全機制  

**適用對象：** 系統架構師、開發人員、維護工程師  

---

### 02_Design_Specification_Mapping.md  
**MT Gateway 設計規格與程式碼對應分析**  
詳細分析 `MT_GW-DS.md` 設計規格與實際程式碼的對應關係：  
- 系統概述與程式實現的對應  
- 資料結構的設計規格與程式碼實現  
- 流程圖與實際處理邏輯的對應  
- 長簡訊支援的設計與實現  
- 編碼支援和設定檔對應  

**適用對象：** 系統分析師、程式設計師、技術主管  

---

### 03_Configuration_Mapping_Table.md  
**SMS 系統程式碼與設定檔對應關係表**  
完整的程式與設定檔對應關係表格，包含：  
- 主要設定檔與程式對應表  
- SystemD 服務設定對應表  
- 編譯設定與函式庫對應表  
- 共用記憶體與映像檔對應表  
- 日誌檔案與程式對應表  
- 網路設定、安全設定、效能調整對應表  

**適用對象：** 系統管理員、運維工程師、故障排除人員  

---

### 04_System_Architecture_Overview.md  
**MTSMS 系統整體架構文件**  
完整的系統架構總覽，包含：  
- 系統分層架構與程式分佈圖  
- 完整程式清單（核心服務、輔助程式、工具程式）  
- 程式間資訊交換機制（共用記憶體、檔案系統、網路通訊）  
- 各程式設定檔及作用詳解  
- 系統設定檔層次結構  

**適用對象：** 系統架構師、技術主管、新進開發人員  

---

## 快速導航

### 依使用場景分類
🏗 **系統架構了解**  
- 參考：`04_System_Architecture_Overview.md`（推薦起點）  
- 重點：整體架構圖、程式清單、資訊交換機制  

🔧 **系統部署和設定**  
- 參考：`04_System_Architecture_Overview.md` + `03_Configuration_Mapping_Table.md`  
- 重點：SystemD 服務設定、設定檔作用、網路設定  

🔍 **故障排除和除錯**  
- 參考：`01_DataFlow_Analysis.md` + `03_Configuration_Mapping_Table.md`  
- 重點：錯誤處理機制、日誌檔案對應、控制檔案狀態  

💻 **程式開發和維護**  
- 參考：`02_Design_Specification_Mapping.md`  
- 重點：資料結構對應、流程圖對應、編碼支援  

📊 **系統監控和效能調整**  
- 參考：`01_DataFlow_Analysis.md`（效能優化章節）  
- 參考：`03_Configuration_Mapping_Table.md`（效能調整對應表）  

---

### 依系統模組分類
📨 **訊息接收與驗證**  
- Input Buffer、用戶驗證、訊息處理相關章節  
- 對應程式：`SMPP_server.c`, `receive.c`, `verify.c`  

🔄 **訊息路由與調度**  
- Message Pool、路由決策、SMSC 通訊相關章節  
- 對應程式：`summit.c`, `dispatcher.c`, `sender.c`  

📞 **傳遞報告處理**  
- Receive Buffer、DR 驗證、狀態回報相關章節  
- 對應程式：`receive_opensmpp_cdr.c`, `url_client.c`  

📱 **長簡訊支援**  
- 長簡訊資料結構、分割邏輯、狀態追蹤相關章節  
- 對應程式：`mt2.c`, `lsms_client.c`, `lsum.c`  

---

## 系統架構概述

```
graph TD

Client --> SMPP_Server["SMPP Server"] --> Input_Buffer["Input Buffer"] --> Summit --> SMSC
Input_Buffer --> Message_Pool["Message Pool"]
Summit --> Dispatcher
Dispatcher --> Message_Pool
Message_Pool --> Receive_Buffer["Receive Buffer"]
Receive_CDR["Receive CDR"] --> Receive_Buffer
Receive_Buffer --> Verify
Verify --> url_client["URL Client"]
Verify --> DR_Server["DR Server"]

```
---

## 重要檔案快速參考

### 核心執行檔
- `home/igw/bin/SMPP_server` — 主要 SMPP 服務  
- `home/igw/bin/summit` — 訊息調度服務  
- `home/igw/bin/verify` — 驗證服務  
- `home/igw/bin/mt2` — 長簡訊處理  

### 關鍵設定檔
- `/etc/sms_check_ip` — IP 白名單  
- `/etc/sms_cdr_server_ip` — CDR 伺服器 IP  
- `/etc/sms_return_ip` — DR 回報 IP  
- `/etc/sms_ton_npi` — TON/NPI 設定  

### 控制檔案
- `/ramdisk/.s` — Input Buffer 狀態  
- `/ramdisk/.u` — Message Pool 狀態  
- `/ramdisk/.r` — Receive Buffer 狀態  

### 日誌目錄
- `home/igw/log/SMPP_server_log/` — 服務日誌  
- `home/igw/log/SMSC_LOG/` — SMSC 通訊日誌  
- `home/igw/log/INTERNAL_LOG/` — 內部處理日誌  

---

## 使用建議
1. **初次接觸系統**：先閱讀 `01_DataFlow_Analysis.md` 了解整體流程  
2. **深入開發**：參考 `02_Design_Specification_Mapping.md` 理解設計原理  
3. **系統維護**：使用 `03_Configuration_Mapping_Table.md` 作為參考手冊  
4. **故障排除**：結合流程分析與設定對應表，快速定位問題  

---

## 更新說明
- **文件版本**：v1.0  
- **最後更新**：2025-08-27  
- **更新者**：Claude Code Analysis  
- **基於程式碼版本**：commit `415430d`  

---

## 注意事項
1. 所有路徑均為 Windows 格式，實際部署請依作業系統調整  
2. 設定檔修改後需重啟相應服務才能生效  
3. 共用記憶體修改需謹慎，建議先備份映像檔  
4. 日誌檔案會自動輪轉，需注意磁碟空間管理  

---

如需更多資訊或補充文件內容，請參考 `CLAUDE.md` 中的開發指導原則。

