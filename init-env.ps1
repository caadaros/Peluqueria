$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

if (Test-Path .env) {
    Write-Host ".env ya existe; no se sobrescribe."
    exit 0
}

function Nuevo-Secreto([int]$bytes, [int]$largo) {
    $buf = New-Object byte[]$bytes
    [System.Security.Cryptography.RandomNumberGenerator]::Fill($buf)
    $texto = [Convert]::ToBase64String($buf) -replace '[/+=]', ''
    return $texto.Substring(0, [Math]::Min($largo,$texto.Length))
}

# Generar un JWT Secret limpio sin caracteres problemáticos para JJWT
$jwtBytes = New-Object byte[] 32 [System.Security.Cryptography.RandomNumberGenerator]::Fill($jwtBytes)
$jwtSecret = ([Convert]::ToBase64String($jwtBytes)) -replace '[/+=]', ''

$admin = Nuevo-Secreto 32 20

$contenido = @(
    "DB_USERNAME=PELUQUERIA",
    "DB_PASSWORD=$(Nuevo-Secreto 32 24)",
    "JWT_SECRET=$jwtSecret",
    "JWT_EXPIRATION_MS=900000",
    "ADMIN_USERNAME=admin",
    "ADMIN_PASSWORD=$admin"
) -join "`n"

[System.IO.File]::WriteAllText((Join-Path $PSScriptRoot ".env"), $contenido + "`n")
Write-Host ".env creado para Oracle. Usuario inicial: admin / $admin"