State.getCurrent()

TODO

# Parser

```java
Module parse(String irText, Iterator<String> dotTexts)
```
1) irText -> ModuleIr
```java
class ModuleIr {
	// Function name to function text
	Map<String, String> functions;
}
```
2) 
```java
for (String dotText : dotTexts) {
	String name = functionNameFromDot(dotText);
	Function function = new Function(moduleIr.functions.get(name), dotText);
	//...
}
```

```java
class Module {
	Map<String, Function> functions;
	Optional<String> currentFunction;
}

class Function {
	int line; // Начало функции
	int lineFromBlockId(String);
	String blockIdFromLine(int);
}
```
