<template>
    <article>
        <div class="title" @click.prevent="goPost" > {{ post.title }} </div>
        <div class="information">By uid {{ userLogin }}, postId {{ post.id }}</div>
        <div class="body">{{ post.text }}</div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
    </article>
</template>

<script>
export default {
    name: "IndexPost",
    props: ["post", "userLogin"],
    methods : {
        goPost: function () {
            this.$root.$emit("onPost", this.post.id);
        }
    }
}

// function findCommentsLength (id) {
//     let res = 0
//     for (let idd in this.comments) {
//         if (this.comments[idd]["postId"] === id) {
//             res += 1
//         }
//     }
//     return res
// }

</script>

<style scoped>

</style>