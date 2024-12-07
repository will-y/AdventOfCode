package util

import java.io.File
import java.nio.charset.Charset

fun genAssets(args: Array<String>) {
    val year = args[0];
    val day = args[1];

    val resourcePath = "src/main/resources/year$year/day$day/";
    val sourcePath = "src/main/java/puzzles/year$year/day$day/";

    File(resourcePath).mkdirs();
    File(sourcePath).mkdirs();
    createResourceFile(resourcePath, "input");
    createResourceFile(resourcePath, "part1-test");
    createResourceFile(resourcePath, "part2-test");

    val template = "package puzzles.year$year.day$day\r\n\r\n" + File("src/main/resources/util/template.txt").readBytes().toString(Charset.defaultCharset());
    createSourceFile(sourcePath, "Part1", template)
    createSourceFile(sourcePath, "Part2",  template.replace("Part1", "Part2"))

}

fun createResourceFile(path: String, name: String) {
    File("$path$name.txt").writeText("");
}

fun createSourceFile(path: String, name: String, value: String) {
    File("$path$name.kt").writeText(value);
}