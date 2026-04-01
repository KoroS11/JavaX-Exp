# JavaX Experiments

This repository contains 5 Java experiments, each placed in a separate folder.

## Structure

- Experiment-1-Calculator
- Experiment-2-Vehicle-Management
- Experiment-3-Book-Inventory
- Experiment-4-Vector-Operations
- Experiment-5-Banking-Application

## Prerequisites

- JDK 8 or above
- PowerShell (for running the helper script on Windows)

## Compile and Run Manually

Use this pattern from the project root:

```powershell
Set-Location "<experiment-folder>"
javac <MainClass>.java
java <MainClass>
```

Examples:

```powershell
Set-Location "Experiment-1-Calculator"
javac MenuDrivenCalculator.java
java MenuDrivenCalculator
```

```powershell
Set-Location "Experiment-5-Banking-Application"
javac BankingApplication.java
java BankingApplication
```

## Quick Compile Check (All Experiments)

From the project root:

```powershell
.\Run-All.ps1
```

This script compiles all experiments one by one and reports success/failure.
