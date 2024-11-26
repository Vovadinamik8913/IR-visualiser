import React, {useState} from 'react';

const TXTpart = ({ title, content, onLineClick }) => {
    const [lineIndex, setLineIndex] = useState(null);

    const handleLineClick = (index) => {
        console.log(`Нажата строка ${index + 1}`);
        setLineIndex(index);
        onLineClick(lineIndex)
    };

    return (
        <div className="window" >
            <h2>{title}</h2>
            {content ? (
                <pre className="txt-win"> <code>
                    {content.split('\n').map((line, index) => (
                        <div key={index}
                             onClick={() => handleLineClick(index)}
                             style={{cursor: "pointer"}}
                        >
                            {index + 1}  {line}
                        </div>
                    ))}
                </code> </pre>
            ) : (
                <p>Загрузите файл .ll для отображения содержимого.</p>
            )}
        </div>
    );
};

export default TXTpart;
