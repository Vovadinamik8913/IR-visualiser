import React from 'react';

const TXTpart = ({ title, content }) => {

    const handleLineClick = (index) => {
        console.log(`Нажата строка ${index + 1}`);
    };

    return (
        <div className="window" >
            <h2>{title}</h2>
            {content ? (
                <pre> <code>
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
