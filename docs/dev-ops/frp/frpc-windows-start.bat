@echo off
setlocal

rem 定义 frpc.exe 的路径
set FRPC_PATH="C:\Program Files\frp_0.60.0_windows_amd64\frp_0.60.0_windows_amd64\frpc.exe"

rem 定义配置文件的路径
set CONFIG_PATH="%~dp0frpc-config.ini"

rem 使用 pushd 临时切换到包含 frpc.exe 的目录
pushd "C:\Program Files\frp_0.60.0_windows_amd64\frp_0.60.0_windows_amd64"

rem 执行 frpc.exe 并指定配置文件
%FRPC_PATH% -c %CONFIG_PATH%

rem 返回原目录
popd

endlocal