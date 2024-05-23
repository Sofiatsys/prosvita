document.addEventListener('DOMContentLoaded', () => {
    let data;
    try {
        data = JSON.parse(document.getElementById('article-content').dataset.content);
    } catch (e) {
        console.error("Error parsing data: ", e);
    }
    const editor = new EditorJS({
        // id of Element that should contain the Editor
        holder: 'article-content',
        autofocus: true,
        readOnly: true,
        // add tools here
        tools: {
            header: Header,
            list: List,
        },
        data: data
    });
    // after the JS code has been executed, remove the "data-content" attribute
    document.getElementById('article-content').removeAttribute('data-content');
})