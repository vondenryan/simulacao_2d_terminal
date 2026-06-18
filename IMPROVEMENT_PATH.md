# Project Improvement Path to 8+/10

## Priority 1: Critical Bug Fixes

### 1. Thread Safety Issues
**File:** `src/com/simulacao_terminal/controllers/InputController.java`
```java
// Line ~30: Add volatile to lastInput
public volatile char lastInput;  // Currently: char lastInput
```

**File:** `src/com/simulacao_terminal/models/GameState.java`
```java
// Line ~20: Add volatile to lastInput
public volatile char lastInput;  // Currently: char lastInput
```

**Why:** The `lastInput` field is shared between InputController (writes) and InputService (reads) threads without proper synchronization.

### 2. Resource Leak Fix
**File:** `src/com/simulacao_terminal/controllers/InputController.java`
```java
// In restoreTerminal(): Close the reader field
private void restoreTerminal() {
    try {
        if (terminal != null) {
            running = false;
            terminal.close();
        }
    } catch (Exception e) {
        e.printStackTrace();  // Don't swallow exceptions silently
    }
    // Add: close reader if it exists
    try { if (reader != null) reader.close(); } catch (Exception e) {}
}
```

---

## Priority 2: Code Quality

### 3. Remove Dead Code
**File:** `src/com/simulacao_terminal/controllers/MapController.java`
```java
// Remove these unused lines (~30-31):
// static Scanner in = new Scanner(System.in);
// static Random random = new Random();
```

**File:** `src/com/simulacao_terminal/services/InputService.java`
```java
// Remove unused DECELERATION constant (~15)
private final float DECELERATION = 0.4f;  // Not used anywhere
```

### 4. Magic Number Cleanup
**File:** `src/com/simulacao_terminal/Main.java`
```java
// Replace magic number 33 with named constant
// Create at top of class:
private static final int TARGET_FPS = 30;
private static final int FRAME_TIME = 1000 / TARGET_FPS;
```

**File:** `src/com/simulacao_terminal/engine/GraphicEngine.java`
```java
// Use the named constant instead of 33 in sub-stepping
```

### 5. Improve Input Sentinel Value
**File:** `src/com/simulacao_terminal/services/InputService.java`
```java
// Replace '°' with a clearer sentinel
// Option 1: Use Character.MIN_VALUE
private char keyPressed = Character.MIN_VALUE;

// Option 2: Use a separate flag
private boolean hasNewInput = false;
private char lastInputChar = 0;
```

### 6. Add Basic Validation
**File:** `src/com/simulacao_terminal/controllers/MapController.java`
```java
// Add bounds checking for map access in getTerrainType() method
```

---

## Priority 3: Documentation

### 7. Add Class-Level Javadoc
Add to each class file (after package statement, before class declaration):
```java
/**
 * [Class description]
 */
```

Classes needing Javadoc:
- `Main` - Entry point description
- `InputController` - Input handling overview
- `MapController` - Terrain generation overview
- `GameState` - Game state management
- `Player` - Player entity
- `GraphicEngine` - Rendering engine
- `InputService` - Input processing
- `GameState` - State holder
- `PerlinNoise` - Noise generation utility

### 8. Add Method-Level Javadoc
For public methods, add:
```java
/**
 * [What method does]
 * @param [name] [description]
 * @return [description]
 */
```

### 9. Update README.md
```markdown
## Usage

### Controls
- `W` or `↑` - Jump
- `A` or `←` - Move Left
- `D` or `→` - Move Right
- `Q` or `ESC` - Quit

### Game Loop
1. Player controls a character on procedurally generated terrain
2. Physics simulation includes gravity and collision detection
3. Collect items and reach the goal area
```

### 10. Add TODO/FIXME Comments for Future Work
Where appropriate, add comments explaining potential improvements:
```java
// TODO: Add config file support for game balance
// TODO: Implement save/load functionality
```

---

## Priority 4: Build Configuration

### 11. Create `build.gradle` (or `pom.xml`)
**File:** `build.gradle`
```gradle
plugins {
    id 'java'
    id 'application'
}

group = 'com.simulacao_terminal'
version = '1.0'

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.jline:jline:3.24.1'
}

application {
    mainClass = 'com.simulacao_terminal.Main'
}

jar {
    manifest {
        attributes 'Main-Class': 'com.simulacao_terminal.Main'
    }
}
```

### 12. Update README Build Instructions
```markdown
### Building
```bash
# With Gradle
./gradlew build

# Or compile manually
javac -cp ".:lib/*" src/com/simulacao_terminal/Main.java
```
```

---

## Priority 5: Testing

### 13. Create Unit Tests
**Directory:** `src/test/java/com/simulacao_terminal/`

**File:** `src/test/java/com/simulacao_terminal/utils/PerlinNoiseTest.java`
```java
import org.junit.Test;
import static org.junit.Assert.*;

public class PerlinNoiseTest {
    @Test
    public void testNoiseGeneration() {
        PerlinNoise noise = new PerlinNoise();
        float value = noise.noise(0, 0);
        assertTrue("Noise value should be between -1 and 1", 
                   value >= -1 && value <= 1);
    }
}
```

**File:** `src/test/java/com/simulacao_terminal/models/PlayerTest.java`
```java
// Test player movement and physics
```

### 14. Add Test Dependency to Build File
**File:** `build.gradle`
```gradle
dependencies {
    implementation 'org.jline:jline:3.24.1'
    testImplementation 'junit:junit:4.13.2'
}
```

---

## Priority 6: Architecture Improvements

### 15. Separate Update and Render Logic
**File:** `src/com/simulacao_terminal/engine/GraphicEngine.java`

Current: `updatePhysics()` and `render()` are mixed
Desired: Split into distinct `update()` and `render()` methods

```java
public void update(GameState gs, Player player) {
    // Physics updates only
}

public void render(GameState gs, Player player, char[][] map) {
    // Rendering only
}
```

### 16. Refactor MapController for Encapsulation
**File:** `src/com/simulacao_terminal/controllers/MapController.java`

```java
// Instead of passing entire map array to InputService
// Add getter methods for specific map data:
public class MapController {
    public int getTerrainTypeAt(int x, int y) {
        // Bounds-checked access
    }
    
    public int getMapWidth() { return width; }
    public int getMapHeight() { return height; }
}
```

---

## Priority 7: Configuration Support

### 17. Add Config File Support
**File:** `config.json`
```json
{
  "gravity": 0.5,
  "jumpPower": 8.0,
  "acceleration": 0.8,
  "maxSpeed": 6.0,
  "fps": 30,
  "mapWidth": 200,
  "mapHeight": 50,
  "seed": 12345
}
```

### 18. Create ConfigReader Utility
**File:** `src/com/simulacao_terminal/utils/ConfigReader.java`
```java
public class ConfigReader {
    public static GameConfig load(String path) {
        // Load and parse JSON config
    }
}
```

---

## Estimated Effort by File

| File | Priority | Estimated Changes |
|------|----------|-------------------|
| InputController.java | 1 | 2 modifications |
| GameState.java | 1 | 1 modification |
| MapController.java | 2, 6 | 4 modifications |
| InputService.java | 2, 6 | 2 modifications |
| GraphicEngine.java | 6 | 2 modifications |
| Main.java | 2 | 3 modifications |
| README.md | 3, 4 | 15+ lines added |
| build.gradle | 4 | New file |
| JUnit tests | 5 | 2+ new files |
| ConfigReader.java | 7 | New file |
| config.json | 7 | New file |

---

## Grade Increase Expectations

| Priority | Expected Grade Impact |
|----------|----------------------|
| 1 (Critical) | +1.5 |
| 2 (Code Quality) | +1.0 |
| 3 (Documentation) | +1.0 |
| 4 (Build Config) | +0.5 |
| 5 (Testing) | +1.0 |
| 6 (Architecture) | +1.0 |
| 7 (Configuration) | +0.5 |
| **Total** | **+6.5** (from 6.5 → 10) |

Note: Some improvements may overlap or provide combined benefits. The grade increase is estimated.
