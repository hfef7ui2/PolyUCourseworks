Public Class Form1
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Dim TempItem As ListViewItem
        Dim TempItem2 As ListViewItem.ListViewSubItem
        TempItem = ListView1.Items.Add(TextBox1.Text)
        TempItem2 = TempItem.SubItems.Add(TextBox2.Text)

        TempItem.SubItems.Add(ComboBox1.SelectedItem.ToString)

        For i = 0 To ComboBox1.Items.Count - 1
            If TreeView1.Nodes(i).Text = ComboBox1.SelectedItem.ToString() Then
                Dim tn As TreeNode
                tn = TreeView1.Nodes(i).Nodes.Add(TextBox1.Text)
            End If
        Next
        MessageBox.Show("Add Successfully", "Notice")
    End Sub

    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        For i = 0 To ComboBox1.Items.Count - 1
            TreeView1.Nodes.Add(ComboBox1.Items(i))
        Next
    End Sub
End Class
