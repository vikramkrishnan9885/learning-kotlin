gradle init --type java-library
rm -rf src/main/java
rm -rf src/test
mkdir -p src/main/kotlin
echo """
fun main(args:Array<String>){
    println(\"Hello World!\")
}""" > src/main/kotlin/Hello.kt

echo """
buildscript{
  ext.kotlin_version = '1.3.61'
  repositories {
    jcenter()
  }
  dependencies {
    classpath \"org.jetbrains.kotlin:kotlin-gradle-plugin:\$kotlin_version\"
  }
}
apply plugin:'kotlin'
repositories{
    jcenter()
}
dependencies{
    compile \"org.jetbrains.kotlin:kotlin-stdlib:\$kotlin_version\"
}
""" > build.gradle

gradle build