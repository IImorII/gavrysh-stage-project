
let clientApi = Vue.resource('/clients{/id}');

function indexOf(list, id) {
    for(let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('client-row', {
        props: ['client', 'clients', 'editMethod'],
        template: '<div>' +
            '<i>({{ client.id }})</i> {{ client.name }}' +
            '<span>' +
                '<input type="button" value="Edit" @click="edit" />' +
                '<input type="button" value="X" @click="del" />' +
            '</span>' +
            '</div>',
        methods: {
            edit: function () {
                this.editMethod(this.client);
            },
            del: function () {
                clientApi.remove({id: this.client.id}).then(result => {
                    if (result.ok) {
                        this.clients.splice(this.clients.indexOf(this.client), 1)
                    }
                })
            }
        }
});

Vue.component('clients-list', {
        props: ['client', 'clients'],
        data: function () {
            return {
                message: null
            }
        },
        template: '<div>' +
            '<client-form :clients="clients" :clientAttr="client" />' +
            '<client-row v-for="client in clients" :key="client.id" :client="client" :editMethod="editMethod" :clients="clients"/>' +
            '</div>',
        created: function () {
            clientApi.get().then(result =>
                result.json().then(data =>
                    data.forEach(client => this.clients.push(client))
                )
            )
        },
        methods: {
            editMethod: function (client) {
                this.client = client;
            }
        }
});

Vue.component('client-form', {
    props: ['clients', 'clientAttr'],
    data: function () {
        return {
            name: '',
            id: ''
        }
    },
    watch: {
        clientAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
            '<input type="text" placeholder="Input name" v-model="name" />' +
            '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            let client = { name: this.name };

            if (this.id) {
                clientApi.update({id: this.id}, client).then(result =>
                    result.json().then(data => {
                        let index = indexOf(this.clients, data.id);
                        this.clients.splice(index, 1, data);
                        this.name = '';
                        this.id = '';
                    })
                )
            } else {
                clientApi.save({}, client).then(result =>
                    result.json().then(data => {
                        this.clients.push(data)
                        this.name = ''
                    })
                )
            }
        }
    }
});

let app = new Vue(
    {
        el: '#app',
        template: '<clients-list :clients="clients" />',
        data: {
            clients: []
        }
    }
);

