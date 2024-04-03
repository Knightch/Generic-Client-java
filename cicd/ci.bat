CALL mvn clean install -DskipTests=true -Dmaven.javadoc.skip=true

mkdir target\dependency
cd target\dependency
jar -xf ..\*.jar
cd ..\..

docker build -t generic-client .