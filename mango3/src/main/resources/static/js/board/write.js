const editorElem = document.querySelector('#editor');
const writeFrmElem = document.querySelector('#writeFrm');

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

function onImageRemoveEvent(event) {
	const differ = event.source.differ

    // if no difference
    if (differ.isEmpty) {
        return;
    }

    const changes = differ.getChanges({
        includeChangesInGraveyard: true
    });

    if (changes.length === 0) {
        return;
    }

    let hasNoImageRemoved = true

    // check any image remove or not
    for (let i = 0; i < changes.length; i++){
        const change = changes[i]
        // if image remove exists
        if (change && change.type === 'remove' && change.name === 'image') {
            hasNoImageRemoved = false
            break
        }
    }

    // if not image remove stop execution
    if (hasNoImageRemoved) {
        return;
    }

    // get removed nodes
    const removedNodes = changes.filter(change => (change.type === 'insert' && change.name === 'image'))

    // removed images src
    const removedImagesSrc = [];
    // removed image nodes
    const removedImageNodes = []

    removedNodes.forEach(node => {
        const removedNode = node.position.nodeAfter
        removedImageNodes.push(removedNode)
        removedImagesSrc.push(removedNode.getAttribute('src'))
    })

	//서버 이미지 삭제처리
	removedImagesSrc.forEach(src => {
		console.log(`src : ${src}`);
	})
} 

ClassicEditor
	.create(editorElem, {
		  extraPlugins: [MyCustomUploadAdapterPlugin],
	})
	.then( editor => {
	        console.log( editor );
			editor.model.document.on('change:data', onImageRemoveEvent);
	})
	.catch( error => {
	        console.error( error );
	})
	