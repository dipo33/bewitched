# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

**Bewitched** is a Minecraft 1.7.10 Forge mod for GTNH (GregTech: New Horizons). It adds witchery-flavored content: magical crops, mutating plants, the Mandrake mob, and magical trees. Java 17 syntax is allowed (via Jabel), but the output targets JVM 8.

## Build Commands

```bash
./gradlew build        # Compile and package JAR to build/libs/
./gradlew runClient    # Launch Minecraft client with the mod loaded
./gradlew runServer    # Launch dedicated server
./gradlew idea         # Generate IntelliJ project files
```

There are no unit tests.

## Specs

The `specs/` folder is the authoritative source of truth for what this mod should implement. Before writing any code, read the relevant spec files. Implementation must match the spec exactly.

If anything in the specs is ambiguous, missing, or contradictory, **do not guess** — ask the user to clarify and update the spec first.

## Textures

When adding a new block or item that requires a custom texture, copy the appropriate placeholder from `src/main/resources/placeholders/` to the target texture path:

- `placeholder_block.png` — for block textures (`assets/bewitched/textures/blocks/`)
- `placeholder_item.png` — for item textures (`assets/bewitched/textures/items/`)

This keeps the mod buildable and in-game visible before real artwork is ready.

## Naming Convention

Internal class names use `Bewitched` as the prefix, never `Witch`. Examples: `BlockBewitchedLog`, `BlockBewitchedSapling`. This applies to all blocks, items, entities, and helpers added by this mod.

## Architecture

### Registration Pattern

All blocks and items are declared as `ObjectHolder<T>` static fields — a lazy `Supplier` wrapper that initializes on first `.get()`. Registration happens in `preInit` via `CommonProxy`:

1. `BewitchedBlocks.registerBlocks()` — calls `GameRegistry.registerBlock()` for each block, sets unlocalized/texture names
2. `BewitchedItems.registerItems()` — calls `GameRegistry.registerItem()` for seeds, flowers, roots, armor
3. `BewitchedEntities.registerEntities()` — registers `EntityMandrake` with a global unique ID

Seeds reference their crop block and vice versa, so registration order matters: blocks first, then items.

### Network Layer

`BewitchedNetwork.NET` is a single `SimpleNetworkWrapper`. Messages that touch client-only classes (rendering, sounds) use a no-op "Safe" server handler to avoid classloading issues; the real handler is registered only in `ClientProxy`.

### Proxy Pattern

`CommonProxy` handles server-safe lifecycle hooks (`preInit`, `init`, `postInit`). `ClientProxy` overrides methods to register renderers, particle effects (`EffectRegistry`), sound muffling (`ClientSoundHandler`), and armor models.
