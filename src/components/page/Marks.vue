<template>
    <div class="marks datatable">
        <div class="caption">marks</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>value</th>
                <th>userLogin</th>
                <th>date</th>
                <th>comment</th>
            </tr>
            </thead>
            <tbody>
            <MarkRow v-for="mark in marks" :mark="mark" :key="mark.user.id"/>
            </tbody>
        </table>
    </div>
</template>

<script>
import MarkRow from "@/components/page/MarkRow";

export default {
    name: "Marks",
    components: {MarkRow},
    props: ["marks"],
    beforeUpdate() {
        this.$root.$emit("updateMarks");
        setInterval(this.$root.$emit("updateMarks"), 3000);
    }
}
</script>

<style scoped>

</style>