import React, {useState} from 'react';

const Header = ({ onFileUpload, onDoneRequest,  options, onBuildRequest }) => {
    const [keyName, setKeyName] = useState(''); //key-name
    const [selectedFile, setSelectedFile] = useState(null); // file
    const [selectedOption, setSelectedOption] = useState('');// выбранное значение
    const [funcName, setFuncName] = useState('');

    const handleFileUpload = (event) => {
        const file = event.target.files[0];
        if (file && file.name.endsWith('.ll')) { // Проверяем, что файл имеет расширение .ll
            onFileUpload(file);
            setSelectedFile(file)
        } else {
            alert("Пожалуйста, загрузите файл с расширением .ll");
        }
    };

    const handleKeyChange = (event) => {
        setKeyName(event.target.value); // Обновляем ключ при вводе
    };

    const handleDropdownChange = (event) => {
        setSelectedOption(event.target.value);// Обновляем выбранное значение
        setFuncName(event.target.value);
    };

    // Обработка нажатия на кнопку Готово
    const handleDoneClick = () => {
        if (keyName && selectedFile) {
            onDoneRequest(keyName, selectedFile); // Передаем ключ и файл для запроса
        } else {
            alert('Введите имя папки и загрузите .ll файл');
        }
    };

    // Обработка нажатия на кнопку Получить свг
    const handleBuildClick = () => {
        if (keyName && selectedFile && funcName) {
            onBuildRequest(keyName, selectedFile, funcName); // Передаем ключ, файл и имя функции для запроса
        } else {
            alert('Введите имя папки и загрузите .ll файл');
        }
    };

    return (
        <header className="header">
            <div className="header-left">
                <input
                    type="file"
                    id="file-upload"
                    onChange={handleFileUpload}
                    style={{display: 'none'}}/>
                <label htmlFor="file-upload" className="upload-button">Загрузить файл</label>
                <input
                    type="text"
                    value={keyName}
                    onChange={handleKeyChange}
                    placeholder="Введите имя папки"
                    className="key-input"
                />
                <button onClick={handleDoneClick} className="build-button">Готово</button>
                <select
                    value={selectedOption}
                    onChange={handleDropdownChange}
                    className="dropdown"
                >
                    <option value="start" disabled hidden>Выберите функцию</option>
                    {options.length > 0 ? (
                        options.map((option, index) => (
                            <option key={index} value={option}>
                                {option}
                            </option>
                        ))
                    ) : (
                        <option disabled>Нет доступных функций</option>
                    )}
                </select>
                <button onClick={handleBuildClick} className="build-button">Получить SVG</button>
            </div>
            <h1 className="header-title">IR VISUALIZER</h1>
        </header>
    );
};

export default Header;
