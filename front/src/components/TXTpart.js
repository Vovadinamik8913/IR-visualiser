import React, {useState} from 'react';
import hljs from '../../node_modules/highlight.js/lib/core.js';
import '../../node_modules/highlight.js/styles/a11y-light.css';
import llvm from '../../node_modules/highlight.js/lib/languages/llvm.js';

hljs.registerLanguage('llvm', llvm);

const TXTpart = ({ title, content, onLineClick }) => {
    const [lineIndex, setLineIndex] = useState(null);

    const handleLineClick = (index) => {
        console.log(`Нажата строка ${index + 1}`);
        setLineIndex(index);
        onLineClick(lineIndex);
    };

    return (
        <div className="window" >
            <h2>{title}</h2>
            {content ? (
                <pre className="txt-win">
                    {content.split('\n').map((line, index) => {
                        // Подсвечиваем каждую строку отдельно
                        const highlightedLine = hljs.highlight(line, {language: 'llvm'}).value;
                        return (
                            <div
                                key={index}
                                onClick={() => handleLineClick(index)}
                                style={{display: 'flex'}}
                                dangerouslySetInnerHTML={{
                                    __html: `<span class="line-number">${index + 1}</span> ${highlightedLine}`,
                                }}
                            ></div>
                        );
                    })}
                </pre>
            ) : (
                <p>Загрузите файл .ll для отображения содержимого.</p>
            )}
        </div>
    );
};

export default TXTpart;