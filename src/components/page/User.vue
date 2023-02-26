<template>
    <div class="FindUser form-box">
        <div class="header">findUser</div>
        <div class="body">
            <form @submit.prevent="onFindUserById(id)">
                <div class="field">
                    <div class="name">
                        <label for="id">id</label>
                    </div>
                    <div class="value">
                        <input autofocus id="id" name="id" v-model="id"/>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Find">
                </div>
            </form>
            <template v-if="foundUser">
                <div>
                    <ul>
                        <li>{{ foundUser.id }}</li>
                        <li>{{ foundUser.login }}</li>
                        <li>{{ foundUser.creationTime }}</li>
                    </ul>
                </div>
            </template>
            <template v-else>
                <a>no such user</a>
            </template>


        </div>
    </div>
</template>

<script>

import axios from "axios";

export default {
    name: "User",
    // props: ["foundUser"],
    data: function () {
        return {
            id: "",
            foundUser: null,
            error: ""
        }
    },
    methods: {
        onFindUserById: function (id) {
            axios.get("/api/1/user/" + id, {
                id
            }).then(response => {
                this.foundUser =  response.data;
                // this.$root.$emit("onChangePage", "User");
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            })
        },
    },
}
</script>

<style scoped>

</style>