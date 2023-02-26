<template>
    <div>
        <IndexPost v-for="post in posts" :post="post"
                   :key="post.id" :userLogin="post.user.login"/>
    </div>
</template>

<script>
import IndexPost from "@/components/index/IndexPost";

export default {
    name: "Index",
    components: {IndexPost},
    props: ["posts"],
}

</script>

<style scoped>

</style>
