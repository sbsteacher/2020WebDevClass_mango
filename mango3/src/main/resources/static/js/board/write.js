const editorElem = document.querySelector('#editor') 

ClassicEditor
.create(editorElem)
.then( editor => {
        console.log( editor );
})
.catch( error => {
        console.error( error );
});