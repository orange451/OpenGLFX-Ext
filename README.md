# OpenGLFX-Ext
Add extra features to OpenGLFX project.

# Maven:
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.orange451</groupId>
	<artifactId>OpenGLFX-Ext</artifactId>
	<version>master-SNAPSHOT</version>
</dependency>
```

# Gradle
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.orange451:OpenGLFX-Ext:master-SNAPSHOT'
}
```
