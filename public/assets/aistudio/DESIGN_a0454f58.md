---
name: Academic Offline Material
colors:
  surface: '#fafaf4'
  surface-dim: '#dadad5'
  surface-bright: '#fafaf4'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f4f4ee'
  surface-container: '#eeeee9'
  surface-container-high: '#e8e8e3'
  surface-container-highest: '#e3e3de'
  on-surface: '#1a1c19'
  on-surface-variant: '#3f493f'
  inverse-surface: '#2f312e'
  inverse-on-surface: '#f1f1ec'
  outline: '#6f7a6e'
  outline-variant: '#becabc'
  surface-tint: '#026d33'
  primary: '#00602b'
  on-primary: '#ffffff'
  primary-container: '#1b7a3e'
  on-primary-container: '#abffb9'
  inverse-primary: '#80da93'
  secondary: '#556158'
  on-secondary: '#ffffff'
  secondary-container: '#d9e6da'
  on-secondary-container: '#5b675e'
  tertiary: '#7f4300'
  on-tertiary: '#ffffff'
  tertiary-container: '#a25700'
  on-tertiary-container: '#ffe8d8'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#9bf7ad'
  primary-fixed-dim: '#80da93'
  on-primary-fixed: '#00210b'
  on-primary-fixed-variant: '#005225'
  secondary-fixed: '#d9e6da'
  secondary-fixed-dim: '#bdcabe'
  on-secondary-fixed: '#131e17'
  on-secondary-fixed-variant: '#3e4a41'
  tertiary-fixed: '#ffdcc3'
  tertiary-fixed-dim: '#ffb77d'
  on-tertiary-fixed: '#2f1500'
  on-tertiary-fixed-variant: '#6e3900'
  background: '#fafaf4'
  on-background: '#1a1c19'
  surface-variant: '#e3e3de'
  surface-light: '#F9FAF8'
  surface-card: '#FFFFFF'
  text-primary: '#1A1C19'
  text-secondary: '#49454F'
  error-destructive: '#E41F1F'
  warning-notice: '#FFD700'
  warning-contrast: '#D97706'
  status-ready: '#1B7A3E'
  status-installed: '#2E7D32'
  status-downloading: '#0284C7'
  status-unsupported: '#64748B'
  surface-dark: '#111411'
  surface-card-dark: '#1E221E'
  primary-dark: '#76DA8E'
typography:
  headline-lg:
    fontFamily: Roboto Flex
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: Roboto Flex
    fontSize: 26px
    fontWeight: '700'
    lineHeight: 32px
  headline-md:
    fontFamily: Roboto Flex
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 30px
  headline-sm:
    fontFamily: Roboto Flex
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 26px
  title-md:
    fontFamily: Roboto Flex
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Source Sans 3
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Source Sans 3
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Source Sans 3
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Roboto Flex
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
  label-md:
    fontFamily: Roboto Flex
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
  label-sm:
    fontFamily: Roboto Flex
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 14px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-tablet: 1.5rem
  margin: 1rem
  margin-tablet: 1.5rem
  margin-desktop: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system establishes an offline-first, academically grounded interface built for university students navigating research, lectures, and machine learning models on Android devices. Rooted in Material 3 principles with institutional gravitas, it combines structural clarity, tactile efficiency, and scholarly authority.

The aesthetic leans on modern academic discipline: calm parchment-tinted surfaces, deep evergreen accents, clean technical hierarchy, and immediate physical reassurance. It rejects fleeting decorative trends in favor of rock-solid ergonomics, high-legibility typographic balance, and unambiguous system states. 

Offline status, local model execution (Edge AI), and document citations are treated as primary citizen interfaces rather than hidden background utilities. Touch targets maintain strict adherence to Android's 48dp baseline for comfortable single-handed use across diverse physical environments—from busy transit to low-light study halls.

## Colors

The palette establishes an academic environment through deep foliage green and calibrated neutral surfaces.

- **Primary (`#1B7A3E`):** Represents institutional authority, stability, and focus. Used for key interactive drivers, active navigation indicators, progress meters, and confirmed states.
- **Secondary (`#E8F5E9`):** Soft, desaturated tint container used for interactive chips, secondary containers, and relaxed badges.
- **Tertiary & Warnings (`#FFD700` / `#D97706`):** Signals hardware throttle, offline execution alerts, and network sync notifications without causing undue panic. `#D97706` provides WCAG AA accessible contrast against light surfaces.
- **Surface Foundations:** `#F9FAF8` anchors light mode as a soft, non-glare surface that reduces eye fatigue across extensive reading sessions. `#FFFFFF` elevates content cards and focus modules.
- **Local Model Status Tokens:** Distinct, functional swatches reserved for on-device ML operational tiers:
  - *Ready:* Active inference primed (`#1B7A3E`).
  - *Installed:* Local weights verified on device storage (`#2E7D32`).
  - *Downloading:* Model weights streaming with transfer progress (`#0284C7`).
  - *Unsupported:* Hardware acceleration / NPU limitation (`#64748B`).

## Typography

Typographic scale is structured around academic readability and dense reference materials.

- **Headlines & Labels (`Roboto Flex`):** Provides a systematic, structural cadence. Its variable engineering adapts flawlessly to Android screen densities, maintaining clean weight metrics from tight status indicators to major screen headers.
- **Reading Body (`Source Sans 3`):** Clean, open humanist letterforms optimized for long-form paper reading, AI thesis summarization, and dense inline citations without causing visual fatigue.
- **Academic Numerics & Metrics:** Dense metrics like memory usage, token speeds (tok/s), and footnote indices rely on tabular lining figures within labels to prevent layout jittering during real-time model streaming.

## Layout & Spacing

Layout geometry follows an 8dp baseline grid (with 4dp sub-steps for compact badges and indicators).

- **Mobile Rhythm:** 16dp (`margin: 1rem`) screen margins ensure thumbs naturally clear interactive content. Gutters remain strict at 16dp.
- **Touch Boundaries:** All touch targets maintain a non-negotiable minimum bounding footprint of `48dp x 48dp`, regardless of visual padding.
- **Adaptive Breakpoints:**
  - *Compact (<600dp):* Single-column vertical flow anchored by the persistent bottom navigation bar.
  - *Medium (600dp - 839dp):* Dual-column layout (Workspace / Document reader alongside AI chat dock); bottom bar transforms into an M3 Navigation Rail on the start edge.
  - *Expanded (840dp+):* 12-column persistent research layout with multi-document split view and docked local model controls.

## Elevation & Depth

Visual depth follows the Material 3 tonal elevation model, relying primarily on surface-color tinting rather than harsh drop shadows.

- **Elevation Level 0 (Base Surface):** Flat canvas (`#F9FAF8`). No shadow. Used for document views and background layouts.
- **Elevation Level 1 (Default Cards & Shelves):** Pure white container (`#FFFFFF`) layered on the canvas with a soft ambient outline (`rgba(26, 28, 25, 0.08)`) and an ultra-diffused shadow (`0px 1px 3px rgba(0, 0, 0, 0.05)`).
- **Elevation Level 2 (Floating Status & Citations):** Used for bottom app bars, contextual citation sheets, and model progress notifications. Tinted with a 3% primary wash in light mode; shadow cast `0px 2px 6px rgba(0, 0, 0, 0.08)`.
- **Elevation Level 3 (Dialogs & Local Model Management Drawers):** High-focus sheets; cast `0px 4px 12px rgba(0, 0, 0, 0.12)`.

## Shapes

The design uses standard Material 3 rounded geometric containers (`roundedness: 2` = 8px baseline / 0.5rem).

- **Cards and Containers:** Feature balanced 16dp (`rounded-lg`) to 20dp radii, softening dense research dashboards and academic feeds.
- **Small Components:** Badges, text inputs, and citation tags utilize standard 8dp (`0.5rem`) corners for crisp structural organization.
- **Pill Exceptions:** Bottom navigation active indicators, filter chips, and floating action triggers use full pill rounding (`9999px`) to immediately communicate pressable ergonomics.

## Components

### Buttons
- **Filled Primary:** Background `#1B7A3E`, text `#FFFFFF`, 48dp minimum height, 20dp horizontal padding. Border radius 12dp. Pressed state shifts to an overlay of 12% black.
- **Tonal / Secondary:** Background `#E8F5E9`, text `#1B7A3E`. Border radius 12dp. Used for secondary workspace actions.
- **Outlined:** 1dp border `#72796F`, transparent background, text `#1B7A3E`.

### Cards
- **Container:** Pure `#FFFFFF` fill, 16dp or 20dp border-radius, 16dp internal padding.
- **Variants:**
  - *Standard Card:* Soft 1dp outer border (`rgba(0,0,0,0.06)`).
  - *Offline Alert Card:* Left accent border 4dp in `#D97706`, warning light tint `#FFFBEB`.

### Local Model Status Badges
- **Installed:** Pill shape, background `#E8F5E9`, text and indicator icon `#2E7D32`.
- **Downloading:** Pill shape, background `#E0F2FE`, text `#0284C7`, includes an inline 12dp circular progress indicator.
- **Ready:** Background `#1B7A3E`, text `#FFFFFF`, bold verification dot.
- **Unsupported:** Background `#F1F5F9`, text `#64748B`, with an inline warning badge.

### Academic Citation Chips
- Small, durable components embedded inside AI text streams.
- Dimensions: 24dp height, 8dp corner radius, 8dp horizontal padding.
- Style: `#E8F5E9` background, `#1B7A3E` text (`label-sm`), hyperlinked directly to the offline document source coordinate.

### Bottom Navigation (5 Destinations)
- **Destinations:** Home, Workspaces, Chat, Study, Library.
- Height: 80dp total bar height anchored to the system navigation bar.
- Active pill indicator: 64dp wide by 32dp high container in `#E8F5E9`, with active icon and label in `#1B7A3E`.
- Inactive items: `#49454F` icon and text label.
- Min touch target: 48dp x 48dp centered per destination.

### Text Inputs & Form Fields
- **M3 Outlined Field:** 56dp height, 12dp corner radius, 1dp border `#72796F`. Focus border expands to 2dp `#1B7A3E`. Label transitions gracefully to top cut-out.

### Checkboxes & Radio Controls
- Minimum 48dp touch footprint. Visual target 20dp with 4dp rounded corner (checkbox) or circular ring (radio). Active fill `#1B7A3E`.