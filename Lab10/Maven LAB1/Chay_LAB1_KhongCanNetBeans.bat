@echo off
chcp 65001 >nul
title Chay chuong trinh LAB 1 - Ma Hoa Co Dien
echo ==========================================================
echo   DANG BIEN DICH VA CHAY LAB 1 - MA HOA DOI XUNG CO DIEN
echo ==========================================================

set "PROJECT_DIR=%~dp0LAB1_MaHoaCoDien"
set "SRC_DIR=%PROJECT_DIR%\src\main\java\week_01"
set "OUT_DIR=%PROJECT_DIR%\target\classes"
set "JDBC_JAR=%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar"
set "DOCX_SRC=%USERPROFILE%\.gemini\antigravity\brain\3d3b1786-f9a1-420c-b6f2-c480fbfb6b60\scratch\BaoCao_LAB1_MaHoaCoDien.docx"

if exist "%DOCX_SRC%" (
    if not exist "%~dp0BaoCao_LAB1_MaHoaCoDien.docx" (
        copy /Y "%DOCX_SRC%" "%~dp0BaoCao_LAB1_MaHoaCoDien.docx" >nul
        echo [OK] Da tao file Word: %~dp0BaoCao_LAB1_MaHoaCoDien.docx
    )
)

if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

pushd "%SRC_DIR%"
javac -encoding UTF-8 -cp "%JDBC_JAR%" -d "%OUT_DIR%" *.java
set "ERR=%errorlevel%"
popd

if %ERR% neq 0 (
    echo [LOI] Bien dich that bai!
    pause
    exit /b %ERR%
)

echo [OK] Bien dich thanh cong! Dang mo cua so frm_Menu...
start "" javaw -cp "%OUT_DIR%;%JDBC_JAR%" week_01.frm_Menu
