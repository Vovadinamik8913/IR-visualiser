import React, {useState} from 'react';

const Header = ({onFileUpload, onDoneRequest, functions, funcFromLIne, onBuildRequest}) => {
    const [folderName, setFolderName] = useState(''); //folder name
    const [selectedFile, setSelectedFile] = useState(null); // file
    const [selectedFunction, setSelectedFunction] = useState('');// выбранное значение
    const [funcName, setFuncName] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleFileUpload = async (event) => {
        const file = event.target.files[0];
        if (file && file.name.endsWith('.ll')) { // Проверяем, что файл имеет расширение .ll
            setIsLoading(true);
            await onFileUpload(file); // Ожидаем завершения запроса
            setSelectedFile(file);
            setFolderName('');
            setIsLoading(false);
        } else {
            alert("Пожалуйста, загрузите файл с расширением .ll");
        }
    };

    const handleKeyChange = (event) => {
        setFolderName(event.target.value); // Обновляем ключ при вводе
    };

    const handleDropdownChange = (event) => {
        setSelectedFunction(event.target.value);// Обновляем выбранное значение
        setFuncName(event.target.value);
    };

    // Обработка нажатия на кнопку Готово
    const handleDoneClick = async () => {
        if (folderName && selectedFile) {
            setIsLoading(true);
            await onDoneRequest(folderName, selectedFile); // Ожидаем завершения запроса
            setIsLoading(false);
        } else {
            alert('Введите имя папки и загрузите .ll файл');
        }
    };

    // Обработка нажатия на кнопку Получить свг
    const handleBuildClick = async () => {
        if (funcName) {
            setIsLoading(true);
            await onBuildRequest(funcName); // Ожидаем завершения запроса
            setIsLoading(false); // Передаем ключ, файл и имя функции для запроса
        } else {
            alert('Выберите функцию');
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
                <label
                    htmlFor="file-upload"
                    className="upload-button">
                    { selectedFile ? selectedFile.name : 'Загрузите файл'}
                </label>
                <input
                    type="text"
                    value={folderName}
                    onChange={handleKeyChange}
                    placeholder="Введите имя папки"
                    className="key-input"
                />
                <button
                    onClick={handleDoneClick}
                    className="build-button">
                    Готово
                </button>
                <select
                    value={selectedFunction}
                    onChange={handleDropdownChange}
                    className="dropdown"
                >
                    <option value="start">Выберите функцию</option>
                    {functions.length > 0 ? (
                        functions.map((func, index) => (
                            <option key={index} value={func}>
                                {func}
                            </option>
                        ))
                    ) : (
                        <option disabled>Нет доступных функций</option>
                    )}
                </select>
                <button
                    onClick={handleBuildClick}
                    className="build-button">
                    Получить SVG
                </button>
            </div>
            <h1 className="header-title">IR VISUALIZER</h1>
            {isLoading && <div className="loading-bar"></div>}
        </header>
    );
};

export default Header;
