<template>
    <tr>
        <td>{{user.id}}</td>
        <td>{{ user['login'] }}</td>
        <td>{{ user['creationTime'] }}</td>
        <td>{{ user.admin }}</td>
    </tr>
</template>

<script>
export default {
    name: "UsersRow",
    props: ["user"]
}
</script>

<style scoped>

</style>