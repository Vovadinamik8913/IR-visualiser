import React, {useState} from 'react';
import Header from './components/Header';
import SVGpart from './components/SVGpart';
import TXTpart from "./components/TXTpart";
import './App.css';

function App() {
    const [llContent, setLlContent] = useState(null); // Содержимое .ll файла
    const [listOfFunctions, setListOfFunctions] = useState([]);
    const [svgContent, setSvgContent] = useState(''); // Содержимое SVG
    const [funcFromLine, setFuncFromLine] = useState('');
    const [irId, setIrId] = useState(0);

    // Функция для загрузки .ll файла
    const handleFileUpload = (file) => {
        if (file.name.endsWith('.ll')) { // Проверка расширения
            const reader = new FileReader();
            reader.onload = (event) => {
                setLlContent(event.target.result); // Сохраняем текстовое содержимое .ll файла
                setSvgContent('');
                setListOfFunctions([]);
            };
            reader.readAsText(file);
        } else {
            alert('Пожалуйста, загрузите файл с расширением .ll');
        }
    };


    const handleDoneRequest = async (folder, file) => {
        try {
            const buildFormData = new FormData();
            buildFormData.append("folder", folder);
            buildFormData.append("opt", 0);
            buildFormData.append("file", file);

            const buildResponse = await fetch('http://localhost:8080/files/build/file', {
                method: 'POST',
                body: buildFormData,
            });

            const doneRes = await buildResponse.text();
            console.log(doneRes);

            if (!doneRes) {
                alert('Произошла ошибка при отправке файла');
            } else {
                const id = parseInt(doneRes, 10);// Преобразуем строку в целое число
                console.log(id);
                if (isNaN(id)) {
                    throw new Error('Сервер вернул некорректный ID');
                }
                setIrId(id);

                if (id) {
                    const getFunctionsFormData = new FormData();
                    getFunctionsFormData.append("file", id);

                    const getSvgsResponse = await fetch('http://localhost:8080/files/get/functions', {
                        method: 'POST',
                        body: getFunctionsFormData,
                    });
                    const svgsNames = await getSvgsResponse.json()
                    setListOfFunctions(svgsNames);
                } else {
                    alert('Ошибка: не удалось получить имена функций');
                }
            }
        } catch (error) {
            console.error('Ошибка запроса:', error);
            alert('Произошла ошибка при выполнении запроса "/build/file"');
        }
    };

    const handleBuildRequest = async (funcName) => {
        try {
            const svgFormData = new FormData();
            svgFormData.append("file", irId);
            svgFormData.append("function", funcName);
            const svgResponse = await fetch('http://localhost:8080/files/get/svg', {
                method: 'POST',
                body: svgFormData,
            });
            const svgText = await svgResponse.text();
            console.log(svgResponse);
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
            lineFormData.append("file", irId);
            lineFormData.append("line", index);
            const svgResponse = await fetch('http://localhost:8080/fromline/get/svg', {
                method: 'POST',
                body: lineFormData,
            });
            const svgName = await svgResponse.text();
            setFuncFromLine(svgName);

            try {
                const svgFormData = new FormData();
                svgFormData.append("file", irId);
                svgFormData.append("function", svgName);
                const svgResponse = await fetch('http://localhost:8080/files/get/svg', {
                    method: 'POST',
                    body: svgFormData,
                });
                const svgText = await svgResponse.text();
                console.log(svgResponse);
                setSvgContent(svgText);
            } catch (error) {
                console.error('Ошибка запроса:', error);
                alert('Произошла ошибка при выполнении запроса');
            }

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
