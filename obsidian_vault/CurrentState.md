Immutable - только [[State]] может менять кэшированный `CurrentState` для производительности. `CurrentState` разрешено менять себя только через `overrideBy` для сохранения инвариантов, поддерживаемых [[State]]

[[State]] не гарантирует, что уже полученные инстансы `CurrentState` будут валидны после своего изменения. Значит на каждый реквест от пользователя, и на каждое изменение [[State]] должен отдельно быть вызван `State.getCurrent`

ВАЖНО!
```java
private void overrideBy(CurrentState other)
```
Полностью изменяет своё состояние на состояние переданного объекта
Когда какие либо функции изменяют состояние (например `setLine`), этот метод должен быть вызван со `state.getCurrent()` для сохранения инвариантов. Это нужно, так как [[State]] не гарантирует, что уже полученные инстансы `CurrentState` будут валидны после своего изменения.
Например `setLine` может выглядеть вот так:

```java
public class CurrentState {
	State state;
	Module module;
	//...

	private void overrideBy(CurrentState other) {
		state = other.state;
		module = other.module;
		// Все остальные поля также перезаписываются...
	}
	
	public String setLine(int line) {
		state.setLine(line);
		overrideBy(state.getCurrent());
		return module.blockIdFromLine();
	}
}
```


Block id - это Node0x... (длинное id из дота)

```java
int lineFromBlockId(String blockId)
```
Возвращает строку в изначальном файле

```java
String setLineAndGetBlockId(int line)
```
Текущая функция меняется на ту, которая находится на указанной линии. Возвращает блок на линии.

Другие полезные функции...