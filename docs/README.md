# 2D Terminal Simulation - Documentation

## Overview

This is a Java-based 2D game simulation that renders directly to the terminal. The project demonstrates:
- Procedural terrain generation using Perlin noise
- Physics simulation with gravity, velocity, and collision detection
- Real-time rendering with colorized terminal output
- Multi-threaded architecture for concurrent input, physics, and rendering

## Project Structure

```
com.simulacao_terminal/
├── Main.java                     # Entry point
├── controllers/
│   ├── InputController.java      # Keyboard input handler (JLine)
│   └── MapController.java        # Terrain generation + player spawn
├── models/
│   ├── GameState.java            # Shared game state
│   └── Player.java               # Player entity with physics
├── services/
│   └── InputService.java         # Input processing + physics
├── engine/
│   └── GraphicEngine.java        # Rendering + physics update
└── utils/
    ├── PerlinNoise.java          # Noise generation algorithm
    └── Utils.java                # Console utilities
```

## Documentation Files

| File | Description |
|------|-------------|
| `index.html` | Main documentation index |
| `com.simulacao_terminal/Main.html` | Main class documentation |
| `com.simulacao_terminal/controllers/InputController.html` | Input handling |
| `com.simulacao_terminal/controllers/MapController.html` | Terrain generation |
| `com.simulacao_terminal/models/GameState.html` | Game state management |
| `com.simulacao_terminal/models/Player.html` | Player entity |
| `com.simulacao_terminal/services/InputService.html` | Input processing |
| `com.simulacao_terminal/engine/GraphicEngine.html` | Rendering engine |
| `com.simulacao_terminal/utils/PerlinNoise.html` | Noise algorithm |
| `com.simulacao_terminal/utils/Utils.html` | Console utilities |

## Controls

| Key | Action |
|-----|--------|
| `W` / `↑` / `Space` | Jump |
| `A` / `←` | Move Left |
| `D` / `→` | Move Right |
| `ESC` | Quit |

## Map Block Types

| Value | Block | Color |
|-------|-------|-------|
| 0 | Air | Transparent |
| 1 | Grass | Green |
| 2 | Dirt | Brown |
| 3-4 | Stone | Gray |
| 5 | Sand | Yellow |
| 6 | Water | Cyan |

## Architecture

The game runs three concurrent threads:

1. **Input Controller** - Reads keyboard input via JLine and stores in GameState
2. **Input Service** - Processes input and updates player velocity
3. **Graphic Engine** - Renders the scene and updates physics

## Requirements

- Java 11 or higher
- JLine 3.x library (for terminal input)

## Building and Running

```bash
# Compile
javac -cp ".:lib/*" src/com/simulacao_terminal/Main.java

# Run
java -cp ".:lib/*" com.simulacao_terminal.Main
```

## License

MIT License
