import React, {useState} from 'react';
import Header from './components/Header';
import SVGpart from './components/SVGpart';
import TXTpart from "./components/TXTpart";
import './App.css';

function App() {
    const [llContent, setLlContent] = useState(null); // Содержимое .ll файла
    const [listOfFunctions, setListOfFunctions] = useState([]);
    const [svgContent, setSvgContent] = useState(''); // Содержимое SVG
    const [folderName, setFolderName] = useState('');
    const [fileName, setFileName] = useState('');
    const [funcFromLine, setFuncFromLine] = useState('');

    // Функция для загрузки .ll файла
    const handleFileUpload = (file) => {
        if (file.name.endsWith('.ll')) { // Проверка расширения
            const reader = new FileReader();
            reader.onload = (event) => {
                setLlContent(event.target.result); // Сохраняем текстовое содержимое .ll файла
                setFileName(file.name);
                setSvgContent('');
                setListOfFunctions([]);
            };
            reader.readAsText(file);
        } else {
            alert('Пожалуйста, загрузите файл с расширением .ll');
        }
    };


    const handleDoneRequest = async (folder, file) => {
        setFolderName(folder);
        try {
            const buildFormData = new FormData();
            buildFormData.append("folder", folder);
            buildFormData.append("opt", 0);
            buildFormData.append("file", file);

            const buildResponse = await fetch('http://localhost:8080/llvm/build/file', {
                method: 'POST',
                body: buildFormData,
            });

            if (buildResponse.ok) {
                const doneRes = await buildResponse.text();

                if (doneRes === 'ok') {
                    const getFunctionsFormData = new FormData();
                    getFunctionsFormData.append("folder", folder);
                    getFunctionsFormData.append("filename", file.name);

                    const getSvgsResponse = await fetch('http://localhost:8080/llvm/get/functions', {
                        method: 'POST',
                        body: getFunctionsFormData,
                    });
                    const svgsNames = await getSvgsResponse.json()
                    setListOfFunctions(svgsNames);
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
        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса "/fromline/parse"');
        }
    };

    const handleBuildRequest = async (funcName) => {
        try {
            const svgFormData = new FormData();
            svgFormData.append("folder", folderName);
            svgFormData.append("filename", fileName);
            svgFormData.append("svgname", funcName);
            const svgResponse = await fetch('http://localhost:8080/llvm/get/svg', {
                method: 'POST',
                body: svgFormData,
            });
            const svgText = await svgResponse.text();
            setSvgContent(svgText);
            setFuncFromLine('');
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
                    functions={listOfFunctions}
                    funcFromLIne={funcFromLine}
                    onBuildRequest={handleBuildRequest}
            />
            <div className="main-container">
                <TXTpart
                    title="LLVM IR"
                    content={llContent}
                    onLineClick={handleLineClick}
                />
                <SVGpart title="SVG" svgContent={svgContent}/>
            </div>
        </div>
    );
}

export default App;
