<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>creationTime</th>
                <th>admin</th>
            </tr>
            </thead>
            <tbody>
            <UsersRow v-for="user in users" :user="user" :key="user.id"/>
            </tbody>
        </table>
    </div>
</template>

<script>
import UsersRow from "@/components/page/UsersRow";

export default {
    name: "Users",
    components: {UsersRow},
    props: ["users"]
}
</script>

<style scoped>

</style>