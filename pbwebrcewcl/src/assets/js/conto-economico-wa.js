function updateValues() {
    console.log("conto-economico-wa.js updateValues BEGIN");
    $("input, select, textarea").each(function () {
        var $this = $(this);
        if ($this.is("[type='radio']") || $this.is("[type='checkbox']")) {
            if ($this.prop("checked")) {
                $this.attr("checked", "checked");
            } else {
                $this.removeAttr("checked");
            }
        } else {
            if ($this.is("select")) {
                $this.find(":selected").attr("selected", "selected");
            }
            else if ($this.is("textarea")) {
                $this.text($this.val());
            }
            else {
                $this.attr("value", $this.val());
            }
        }
    });
    console.log("conto-economico-wa.js updateValues END");
}
