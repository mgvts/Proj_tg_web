<template>
    <tr>
        <td>{{mark.id}}</td>
        <td>{{mark.value}}</td>
        <td>{{mark.user.login}}</td>
        <td>{{mark.creationTime}}</td>
        <td>{{mark.comment}}</td>
    </tr>
</template>

<script>
export default {
    name: "UsersRow",
    props: ["mark"]
}
</script>

<style scoped>

</style>