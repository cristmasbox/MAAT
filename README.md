[![](https://jitpack.io/v/cristmasbox/MAAT.svg)](https://jitpack.io/#cristmasbox/MAAT)
# Mobile API for Ancient Texts (MAAT)
A library for rendering egyptian hieroglyphic texts by using the `GlyphX` format.

## Disclaimer
This library only handles the calculation of the position and scale of each hieroglyph. The rendering and storing of the images are not included. Besides the library uses the `GlyphX` code for encoding the hieroglyphic texts.
<br/><br/>
A library for converting `GlyphX` to `MdC` and back is stored here: [GlyphConverter](https://github.com/cristmasbox/GlyphConverter).

> [!TIP]
> **You don't want to handle all the images by yourself?
> Try the [THOTH](https://github.com/cristmasbox/THOTH) library which uses the MAAT library and handles all the images.**\
> It contains an android custom view which renders everything for you. You only have to adjust a few parameters!

## Implementation with jitpack
Add this to your `settings.gradle.kts` at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
}
```
Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation("com.github.cristmasbox:MAAT:1.5.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#cristmasbox/MAAT/)

## Implementation with `.aar` file
Download the `MAAT_debug_versionname.aar` file from this repository, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/MAAT_versionname.aar"))
}
```

> [!IMPORTANT]
> If you renamed the `.aar` file you also have to change the name in the dependencies

## Usage
First add this code to get the ids of all hieroglyphs used in the `GlyphX` document.
```
String GlyphXContent = "";  // Save your GlyphX string here

// Convert your string to an XML Document

DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();                      // Parser that produces DOM object trees from XML content
DocumentBuilder builder;                                                                    // API to obtain DOM Document instance
builder = factory.newDocumentBuilder();                                                     // Create DocumentBuilder with default configuration
Document GlyphXDocument = builder.parse(new InputSource(new StringReader(GlyphXContent)));  // Parse the content to Document object

BoundCalculation boundCalculation = new BoundCalculation(GlyphXDocument);                   // Create an object of the BoundCalculation class
ArrayList<String> ids = boundCalculation.getIds(false, false);                              // Get the ids of all the hieroglyphs e.g. ["sn","n","nw","w","A1"]
```
**The method to get the ids has two parameters:**<br/>
`getIds(boolean lines, boolean rtl)`<br/>
The first parameter is the `writingLayout`:
- `lines`: `true`
- `columns`: `false`
The second parameter is the `writingDirection`:
- `RTL`: `true`
- `LTR`: `false`
> [!IMPORTANT]
> The parameters have to be the same as used in the `BoundProperty`!

The ids are either codes from [Gardiner's Sign List](https://ancientegyptonline.co.uk/Gardiner-sign-list/) like `A1`, `Z1`, `A1B` or `O34` or their [phonetic alternates](http://71.174.62.16/image/WebGlyph/SmallCollection.pdf) like `sn`, `zA`, `xAst` or `ra`.<br/><br/>
In the next step you have to find the right images representing the ids. When you found them, you have to get their dimensions and store them in an `ArrayList<ValuePair<Float, Float>>`. The first `Float`-value is the width and the second is the height of the image.
> [!Caution]
> The width and height values in the `ArrayList` must be in the same order like the equivalent ids.

<br/>

Now you have to setup the `BoundProperty`. This class is included in the library and there you store all the settings for the library.
```
BoundProperty property = new BoundProperty(x, y, textSize,
                            verticalOrientation, writingDirection, writingLayout,
                            drawLines, lineThickness, pagePaddingLeft,
                            pagePaddingTop, pagePaddingRight,
                            pagePaddingBottom, signPadding,
                            layoutSignPadding, interLinePadding);
```
Here is the explanation for the parameters:

- `x` This value defines the global x-position for the whole text. With help of this value, you can move the text as a whole.
- `y` This value defines the global y-position for the whole text. With help of this value, you can move the text as a whole.
- `textSize` This defines the height of a line in pixels. If the `writingLayout` is set to `WRITING_LAYOUT_COLUMNS` this value defines the width of a column in pixels.
- `verticalOrientation` This parameter can only have three values and defines the vertical position of smaller signs (like `n`):
    - `BoundProperty.VERTICAL_ORIENTATION_UP` Put signs to the top of the line
    - `BoundProperty.VERTICAL_ORIENTATION_MIDDLE` Center signs vertically
    - `BoundProperty.VERTICAL_ORIENTATION_DOWN` Drop signs on Baseline
- `writingDirection` This parameter can only have two values and defines the direction of writing:
    - `BoundProperty.WRITING_DIRECTION_LTR` Write from left to right
    - `BoundProperty.WRITING_DIRECTION_RTL` Write from right to left
- `writingLayout` This parameter also have two possible values and determines if signs should be written in lines or in columns:
    - `BoundProperty.WRITING_LAYOUT_LINES` Write signs in lines
    - `BoundProperty.WRITING_LAYOUT_COLUMNS` Write signs in columns
- `drawLines` This determines whether to draw strokes between the lines / columns
- `lineThinkness` This defines the thickness of the stroke if it's enabled
- `paddings`:
  - `pagePadding` This value is separated into `pagePaddingLeft`, `pagePaddingTop`, `pagePaddingRight` and `pagePaddingBottom` and defines the padding of the text as a whole
  - `signPadding` This value defines the padding between two signs which are not grouped
  - `layoutSignPadding` This value defines the relative space between two signs which are in the same group
  - `interLinePadding` This is the space between the lines / columns of the text


Finally get the calculated bounds:
```
ArrayList<ValuePair<Float, Float>> dimensions = new ArrayList<>();  // Store the dimensions of the signs here
ArrayList<Rect> bounds = boundCalculation.getBounds(dimensions, property);
```
Now you can use the position and scale values stored in the `Rect` instances and apply them to your images.

## Version Catalog
### 13.09.2025@1.0.0
This is the first release of the MAAT library.
### 06.12.2025@1.5.0
- Support for ```<br/>``` and ```<pbr/>``` tags added
- Issue with ```RTL``` layout solved
- Paddings for graphical purposes added
### latest Version
`06.12.2025@1.5.0`
