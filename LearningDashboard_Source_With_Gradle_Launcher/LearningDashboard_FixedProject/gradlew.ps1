param([Parameter(ValueFromRemainingArguments = $true)][string[]]$GradleArgs)
$ErrorActionPreference = 'Stop'
$version = '8.9'
$base = Join-Path $env:USERPROFILE '.gradle\local-distributions'
$installDir = Join-Path $base "gradle-$version"
$gradleExe = Join-Path $installDir "gradle-$version\bin\gradle.bat"
if (!(Test-Path $gradleExe)) {
    New-Item -ItemType Directory -Force -Path $base | Out-Null
    $zipPath = Join-Path $base "gradle-$version-bin.zip"
    $url = "https://services.gradle.org/distributions/gradle-$version-bin.zip"
    Write-Host "Gradle $version is missing. Downloading Gradle (one-time setup)..."
    Invoke-WebRequest -Uri $url -OutFile $zipPath -UseBasicParsing
    if (Test-Path $installDir) { Remove-Item $installDir -Recurse -Force }
    New-Item -ItemType Directory -Force -Path $installDir | Out-Null
    Expand-Archive -LiteralPath $zipPath -DestinationPath $installDir -Force
}
if (!(Test-Path $gradleExe)) { throw "Gradle executable was not found after extraction: $gradleExe" }
& $gradleExe @GradleArgs
exit $LASTEXITCODE
