### 使用 Java Spring Boot，開發的**使用者身份驗證系統（登入與註冊）**。該系統需支援：
- Email + 密碼登入。
- **Magic Link 登入**（即一次性登入連結）。
沒有實作 Email 寄送功能，僅模擬 **Magic Link 的產生與驗證邏輯**。

### ✅ Email + 密碼登入：
1. 使用 `email` 與 `password` 註冊帳號。
2. 用記憶體儲存帳戶資訊
3. 密碼必須安全加密
4. 驗證帳號與密碼
---
### 🌐 Magic Link 登入：
- 當使用者請求 Magic Link 時，產生一組 **唯一、限時（10 分鐘內有效）** 的 Token
- 模擬發送 Magic Link
- 使用 token 進行登入
- Token 必須是 **一次性使用**
