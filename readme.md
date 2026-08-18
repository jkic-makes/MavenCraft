# MavenCraft

A 2D voxel sandbox prototype written in Java using Swing/AWT and managed with Apache Maven. Featuring a custom launcher UI, world creation engine supporting Survival and Creative game modes, 60 FPS physics with gravity, collision detection, and block placement mechanics.
---
## How to compile and run
First run command:
```powershell
mvn clean package
```
And then wait and run:
```powershell
java -jar target/minecraft-java-clone-1.0-SNAPSHOT.jar
```
the java command is only if you want to run the game i will keep yall updated

---

## Features

- **Launcher & Menu UI**: Interactive front-page menu and single-player world creation setup using Java Swing `CardLayout`.
- **Multiple Game Modes**:
  - **Survival Mode**: Resource gathering, block durability, and limited block placement counts.
  - **Creative Mode**: Infinite block resources, instant block mining, and unrestricted building.
- **Physics Engine**: Axis-separated collision detection, gravity, jumping, and player bounding-box protection to prevent block trapping.
- **Procedural World Generation**: Dynamic creation of bedrock, stone layers, dirt, grass topsoil, and simple trees.
- **Zero External Dependencies**: Pure Java 17+ standard libraries managed via Apache Maven.

---

## Controls

### Movement & Actions

| Control | Action |
|---|---|
| **A** | Walk Left |
| **D** | Walk Right |
| **W** or **Space** | Jump |
| **Left Click** | Mine / Break Block |
| **Right Click** | Place Selected Block |
| **ESC** | Pause / Return to Main Menu |

### Block Selection Hotbar

Use the number keys **1 through 5** on your keyboard to switch your active placement block:

| Key | Block Selected | Color Preview | Notes |
|---|---|---|---|
| **`1`** | **Dirt** | Brown | Basic building block |
| **`2`** | **Grass** | Green | Surface terrain block |
| **`3`** | **Stone** | Gray | Found underground |
| **`4`** | **Wood** | Brown / Trunk | Tree trunk block |
| **`5`** | **Leaves** | Bright Green | Tree canopy block |

---

## Prerequisites

Ensure you have the following installed on your system:

- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.8+

To verify your installation:
```bash
java -version
mvn -version
