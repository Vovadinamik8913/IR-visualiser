import React, {useState} from 'react';

const Header = ({ onFileUpload, onBuildRequest }) => {
    const [keyName, setKeyName] = useState(''); //key-name
    const [selectedFile, setSelectedFile] = useState(null); // file

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

    // Обработка нажатия на кнопку Build
    const handleBuildClick = () => {
        if (keyName && selectedFile) {
            onBuildRequest(keyName, selectedFile); // Передаем ключ и файл для запроса
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
                <label htmlFor="file-upload" className="upload-button">Upload File</label>
                <input
                    type="text"
                    value={keyName}
                    onChange={handleKeyChange}
                    placeholder="Enter key name"
                    className="key-input"
                />
                <button onClick={handleBuildClick} className="build-button">Build</button>
            </div>
            <h1 className="header-title">IR VISUALIZER</h1>
        </header>
    );
};

export default Header;
