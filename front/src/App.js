import React, { useState } from 'react';
import Header from './components/Header';
import SVGpart from './components/SVGpart';
import TXTpart from "./components/TXTpart";
import './App.css';

function App() {
    const [llContent, setLlContent] = useState(null);
    const [options, setOptions] = useState([]);// Содержимое .ll файла
    const [svgContent, setSvgContent] = useState(''); // Содержимое SVG
    const [folderName, setFolderName] = useState('');

    // Функция для загрузки .ll файла
    const handleFileUpload = (file) => {
        if (file.name.endsWith('.ll')) { // Проверка расширения
            const reader = new FileReader();
            reader.onload = (event) => {
                setLlContent(event.target.result); // Сохраняем текстовое содержимое .ll файла
            };
            reader.readAsText(file);
        } else {
            alert('Пожалуйста, загрузите файл с расширением .ll');
        }
    };

    // Функция для осужествления запросов
    const handleDoneRequest = async (keyName, file) => {
        setFolderName(keyName);
        try {
            const formData = new FormData();
            formData.append("folder", keyName);
            formData.append("opt", 0);
            formData.append("file", file);

            const doneResponse = await fetch('http://localhost:8080/llvm/build/file', {
                method: 'POST',
                body: formData,
            });

            if (doneResponse.ok) {
                const doneRes = await doneResponse.text();

                if(doneRes === 'ok') {
                    const getSvgsFormData = new FormData();
                    getSvgsFormData.append("folder", keyName);
                    getSvgsFormData.append("filename", file.name);

                    const getSvgsResponse = await fetch('http://localhost:8080/llvm/get/functions', {
                        method: 'POST',
                        body: getSvgsFormData,
                    });
                    const svgsNames = await getSvgsResponse.json()
                    setOptions(svgsNames);
                } else {
                alert('Ошибка: не удалось получить имена функций');
                }
            } else {
                alert('Произошла ошибка при отправке файла');
            }
        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса "/build/file"');
        }

        try {
            const parseFormData = new FormData();
            parseFormData.append("file", file);
            await fetch('http://localhost:8080/fromline/parse', {
                method: 'POST',
                body: parseFormData,
            });
        }catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса "/fromline/parse"');
        }
    };

    const handleBuildRequest = async (keyName, file, funcName) => {
        try {
            const svgFormData = new FormData();
            svgFormData.append("folder", keyName);
            svgFormData.append("filename", file.name);
            svgFormData.append("svgname", funcName);
            const svgResponse = await fetch('http://localhost:8080/llvm/get/svg', {
                method: 'POST',
                body: svgFormData,
            });
            const svgText = await svgResponse.text();
            setSvgContent(svgText);

        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса');
        }
    }

    const handleLineClick = async (index) => {
        try {
            const lineFormData = new FormData();
            lineFormData.append("folder", folderName);
            lineFormData.append("line", index);
            const svgResponse = await fetch('http://localhost:8080/fromline/get/svg', {
                method: 'POST',
                body: lineFormData,
            });
            const svgText = await svgResponse.text();
            console.log(svgText);
            setSvgContent(svgText);

        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса');
        }
    }

    return (
        <div className="App">
            <Header onFileUpload={handleFileUpload}
                    onDoneRequest={handleDoneRequest}
                    options={options}
                    onBuildRequest={handleBuildRequest}
            />
            <div className="main-container">
                <TXTpart
                    title="LLVM IR"
                    content={llContent}
                    onLineClick={handleLineClick}
                />
                <SVGpart title="SVG" svgContent={svgContent} />
            </div>
        </div>
    );
}

export default App;
