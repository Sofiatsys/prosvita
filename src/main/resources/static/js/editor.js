document.addEventListener('DOMContentLoaded', () => {
    let data;
    let state = document.getElementById('article-content').dataset.state;
    let readOnly = state === 'read';
    try {
        data = JSON.parse(document.getElementById('article-content').dataset.content);
    } catch (e) {
        console.error("Error parsing data: ", e);
    }
    const editor = new EditorJS({
        // id of Element that should contain the Editor
        holder: 'article-content',
        autofocus: true,
        readOnly: readOnly,
        // add tools here
        tools: {
            header: Header,
            list: List
        },
        data: data,
        onChange: () => {
            if (state === 'create' || state === 'update') {
                editor.save().then((outputData) => {
                    // TODO: sanitize data when creating / saving article
                    /*if (outputData.blocks.length === 0) {
                        console.log('No content to save');
                        return;
                    }
                    let sanitizedOutput = editor.sanitizer.clean(outputData);*/
                    document.getElementById('article-content-hidden').value = JSON.stringify(outputData);
                }).catch((error) => {
                    // TODO handle as alert notification
                    console.log('Saving failed: ', error)
                });
            }
        }
    });
    // after the JS code has been executed, remove the attributes
    document.getElementById('article-content').removeAttribute('data-content');
    document.getElementById('article-content').removeAttribute('data-state');
})