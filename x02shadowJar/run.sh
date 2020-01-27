gradle init --type java-library --dsl=groovy --package=x02shadowJar --test-framework=junit --project-name=x02shadowJar
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
  ext.shadow_version = '5.0.0'
  repositories {
    jcenter()
  }
  dependencies {
    classpath \"org.jetbrains.kotlin:kotlin-gradle-plugin:\$kotlin_version\",
    \"com.github.jengelman.gradle.plugins:shadow:\$shadow_version\"

  }
}
apply plugin:'kotlin'
apply plugin:'com.github.johnrengelman.shadow'

repositories{
    jcenter()
}
dependencies{
    compile \"org.jetbrains.kotlin:kotlin-stdlib:\$kotlin_version\"
}
""" > build.gradle

gradle shadowJar
java -cp build/libs/x02shadowJar-all.jar HelloKt