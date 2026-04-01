$ErrorActionPreference = "Stop"

$experiments = @(
    @{ Folder = "Experiment-1-Calculator"; Main = "MenuDrivenCalculator" },
    @{ Folder = "Experiment-2-Vehicle-Management"; Main = "VehicleManagementSystem" },
    @{ Folder = "Experiment-3-Book-Inventory"; Main = "BookInventorySystem" },
    @{ Folder = "Experiment-4-Vector-Operations"; Main = "VectorOperationsProgram" },
    @{ Folder = "Experiment-5-Banking-Application"; Main = "BankingApplication" }
)

foreach ($exp in $experiments) {
    $folder = Join-Path $PSScriptRoot $exp.Folder
    $javaFile = "$($exp.Main).java"

    Write-Host "`n=== Compiling $javaFile in $($exp.Folder) ===" -ForegroundColor Cyan
    Push-Location $folder
    try {
        javac $javaFile
        if ($LASTEXITCODE -ne 0) {
            throw "Compilation failed for $javaFile"
        }
        Write-Host "Compilation successful: $javaFile" -ForegroundColor Green
    }
    finally {
        Pop-Location
    }
}

Write-Host "`nAll experiments compiled successfully." -ForegroundColor Green
