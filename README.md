# ai-interviewer

aigc for 面试官

### Feature

- 1.通过openai将上传简历返回符合boss直聘格式的简历
- 2.设置prompt,以openai为java面试官,根据上传的简历进行面试，支持memory

### how to use

- 本地开发时，需要将application-prod.yml复制为application-dev.yml并设置自己的redis和openai相关配置

### api

- 上传简历
- ws://127.0.0.1:11001/resume/chat/{返回的简历uuid}