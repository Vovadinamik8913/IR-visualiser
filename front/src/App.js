import React, { useState } from 'react';
import Header from './components/Header';
import SVGpart from './components/SVGpart';
import TXTpart from "./components/TXTpart";
import './App.css';

function App() {
    const [llContent, setLlContent] = useState(null); // Содержимое .ll файла
    const [svgContent, setSvgContent] = useState(''); // Содержимое SVG

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
    const handleBuildRequest = async (keyName, file) => {
        try {
            const formData = new FormData();
            formData.append("key", keyName);
            formData.append("opt", 0);
            formData.append("file", file);

            const buildResponse = await fetch('http://localhost:8080/llvm/build', {
                method: 'POST',
                body: formData,
            });

            if (buildResponse.ok) {
                const buildRes = await buildResponse.text();

                if(buildRes === 'ok') {
                    const svgFormData = new FormData();
                    svgFormData.append("key", keyName);
                    svgFormData.append("filename", file.name);
                    svgFormData.append("svgname", 'main');

                    const svgResponse = await fetch('http://localhost:8080/llvm/get/svg', {
                        method: 'POST',
                        body: svgFormData,
                    });
                    const svgText = await svgResponse.text();
                    setSvgContent(svgText);
                } else {
                alert('Ошибка: не удалось построить SVG');
                }
            } else {
                alert('Произошла ошибка при отправке файла');
            }
        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса');
        }
    };

    return (
        <div className="App">
            <Header onFileUpload={handleFileUpload} onBuildRequest={handleBuildRequest} />
            <div className="main-container">
                <TXTpart title="LLVM IR" content={llContent} />
                <SVGpart title="SVG" svgContent={svgContent} />
            </div>
        </div>
    );
}

export default App;
