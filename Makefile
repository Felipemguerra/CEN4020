run:
	@java -cp ./classes Gradebook

build:
	@mkdir ./classes
	@javac -d ./classes ./src/*.java

clean:
	@rm -r ./classes



