const editorElem = document.querySelector('#editor') 
const writeFrmElem = document.querySelector('#writeFrm')

class MyUploadAdapter {
	constructor( loader ) {        
    	this.loader = loader;
    }

 	upload() {
        return this.loader.file.then(ctntImg => {
				return new Promise((resolve, reject) => {
               		const data = new FormData()
            		data.append('ctntImg', ctntImg)

					fetch('/user/board/uploadImg', {
						method: 'POST',
						body: data
					})
					.then(res => res.json())
					.catch(e => {
						reject('err')
					})										
					.then(myJson => {
						console.log(myJson)
						resolve(myJson)
					})
            	}) 
			} 
		)
    }

 	abort() {}
}

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        // Configure the URL to the upload script in your back-end here!
        return new MyUploadAdapter(loader);
    }
}

ClassicEditor
	.create(editorElem, {
		  extraPlugins: [MyCustomUploadAdapterPlugin],
	})
	.then( editor => {
	        console.log( editor );
	})
	.catch( error => {
	        console.error( error );
	})