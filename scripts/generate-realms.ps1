$Root = Split-Path -Parent $PSScriptRoot

# .env yükle (basit parser)
Get-Content "$Root/.env" |
        Where-Object { $_ -and $_ -notmatch '^\s*#' } |
        ForEach-Object {
            $k, $v = $_ -split '=', 2
            Set-Item "env:$k" $v
        }

# doğrulama
if (-not $env:MICRO_SERVICES_API_SECRET) { throw "MICRO_SERVICES_API_SECRET .env içinde yok" }

# generate
(Get-Content "$Root/realms/ecommerce-realm-template.json" -Raw) `
  -replace '\$\{MICRO_SERVICES_API_SECRET\}', [regex]::Escape($env:MICRO_SERVICES_API_SECRET) `
| Set-Content "$Root/realms/ecommerce-realm.json"